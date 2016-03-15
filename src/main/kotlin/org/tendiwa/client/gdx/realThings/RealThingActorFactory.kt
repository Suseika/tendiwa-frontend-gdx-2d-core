package org.tendiwa.client.gdx.realThings

import com.badlogic.gdx.scenes.scene2d.Actor
import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.space.aspects.name
import org.tendiwa.backend.space.aspects.position
import org.tendiwa.client.gdx.resources.images.NamedTextureCache
import org.tendiwa.client.gdx.walls.WallActor

class RealThingActorFactory(
    private val cache: NamedTextureCache
) {
    fun createActor(realThing: RealThing): Actor {
        val tile = realThing.position.tile
        return WallActor(
            cache.texture("characters/" + realThing.name.string, 0),
            tile.x,
            tile.y
        )
    }
}
