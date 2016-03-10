package org.tendiwa.client.gdx.realThings

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Actor
import org.tendiwa.client.gdx.resources.images.NamedTextureCache
import org.tendiwa.backend.existence.RealThing
import java.util.*

class RealThingActorRegistry {
    private val realThingActorFactory: RealThingActorFactory
    private val thingsToActors: MutableMap<RealThing, Actor> =
    LinkedHashMap()

    init {
        realThingActorFactory =
            RealThingActorFactory(
                NamedTextureCache(
                    TextureAtlas(
                        Gdx.files.classpath("characters/characters.atlas")
                    )
                )
            )
    }

    fun addRealThing(realThing: RealThing) {
        thingsToActors.put(
            realThing,
            realThingActorFactory.createActor(realThing)
        )
    }

    fun actors(): Collection<Actor> =
        thingsToActors.values

    fun actorOf(host: RealThing): Actor =
        thingsToActors[host]!!
}
