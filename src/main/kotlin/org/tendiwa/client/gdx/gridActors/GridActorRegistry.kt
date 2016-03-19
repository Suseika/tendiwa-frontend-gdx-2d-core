package org.tendiwa.client.gdx.gridActors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.space.aspects.position
import org.tendiwa.client.gdx.resources.images.NamedTextureCache
import org.tendiwa.client.gdx.walls.WallActorFactory
import org.tendiwa.frontend.generic.RenderingVicinity
import org.tendiwa.plane.grid.tiles.Tile
import java.util.*

/**
 * Spawns and stores [Actors][Actor] that have a position in space.
 * Different kinds of actors are spawned by different
 * [ActorSpawners][ActorFactory].
 */
class GridActorRegistry(
    vicinity: RenderingVicinity,
    cache: NamedTextureCache,
    private val stage: Stage
) {
    private val tilesToActors =
        LinkedHashMap<Tile, MutableList<Actor>>(50)
    private val thingsToActors: MutableMap<RealThing, Actor> =
        LinkedHashMap()
    private val spawners = ArrayList<ActorFactory>()

    private var wallActorFactory: WallActorFactory

    init {
        wallActorFactory = WallActorFactory(cache, vicinity)
    }

    fun addActorFactory(factory: ActorFactory) {
        spawners.add(factory)
    }

    internal fun rememberActorPosition(tile: Tile, actor: Actor) {
        tilesToActors.getOrPut(tile, { ArrayList() }).add(actor)
    }

    fun actorsAt(tile: Tile): List<Actor> =
        tilesToActors.getOrPut(tile, { ArrayList() })

    fun spawnRealThing(thing: RealThing) {
        if (thingsToActors.containsKey(thing)) {
            println(
                "Trying to spawn an Actor for a RealThing that already has an actor"
            )
            return
        }
        val actor = createActor(thing)
        thingsToActors[thing] = actor
        stage.addActor(actor)
        rememberActorPosition(thing.position.tile, actor)
    }

    private fun createActor(thing: RealThing): Actor =
        spawners
            .find { it.creates(thing) }
            ?.create(thing)
            ?.apply {
                val tile = thing.position.tile
                setPosition(tile.x.toFloat(), tile.y.toFloat())
            }
            ?: throw RuntimeException("No spawner for $thing")

    fun actorOf(thing: RealThing): Actor =
        thingsToActors[thing]!!

    fun removeActor(tile: Tile, thing: RealThing) {
        val actors = tilesToActors[tile]!!
        if (actors.size == 1) {
            tilesToActors.remove(tile)
        } else {
            actors.remove(thingsToActors[thing])
        }
    }

    fun removeWallActor(tile: Tile) {
        val actors = tilesToActors[tile]!!
        if (actors.size == 1) {
            actors[0].remove()
            tilesToActors.remove(tile)
        } else {
            // TODO: This logic assumes wall actors are the only actors on their
            // TODO: tiles.
            tilesToActors[tile]!!.removeAt(0)
        }
    }

    fun spawnWall(tile: Tile) {
        wallActorFactory.createActor(tile)
            .let {
                stage.addActor(it)
                rememberActorPosition(tile, it)
            }
    }
}
