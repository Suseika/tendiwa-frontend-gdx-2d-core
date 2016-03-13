package org.tendiwa.client.gdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.space.aspects.position
import org.tendiwa.client.gdx.realThings.RealThingActorFactory
import org.tendiwa.client.gdx.resources.images.NamedTextureCache
import org.tendiwa.client.gdx.walls.WallActorFactory
import org.tendiwa.frontend.generic.RenderingVicinity
import org.tendiwa.plane.grid.tiles.Tile
import java.util.*

class GridActorRegistry(
    vicinity: RenderingVicinity,
    private val stage: Stage
) {
    private val tilesToActors =
        LinkedHashMap<Tile, MutableList<Actor>>(50)
    private val realThingActorFactory: RealThingActorFactory
    private val thingsToActors: MutableMap<RealThing, Actor> =
        LinkedHashMap()

    private var wallActorFactory: WallActorFactory


    init {
        wallActorFactory =
            WallActorFactory(
                NamedTextureCache(
                    TextureAtlas(Gdx.files.classpath("walls/walls.atlas"))
                ),
                vicinity
            )
        realThingActorFactory =
            RealThingActorFactory(
                NamedTextureCache(
                    TextureAtlas(
                        Gdx.files.classpath("characters/characters.atlas")
                    )
                )
            )
    }

    internal fun rememberActorPosition(tile: Tile, actor: Actor) {
        tilesToActors.getOrPut(tile, { ArrayList() }).add(actor)
    }

    fun actorsAt(tile: Tile): List<Actor> =
        tilesToActors.getOrPut(tile, { ArrayList() })

    fun spawnRealThing(thing: RealThing) {
        val actor = realThingActorFactory.createActor(thing)
        thingsToActors[thing] = actor
        stage.addActor(actor)
        rememberActorPosition(thing.position.tile, actor)
    }

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
