package org.tendiwa.client.gdx.walls

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.tendiwa.client.gdx.RenderingVicinity
import org.tendiwa.world.WallType

class WallActorFactory(
    private val wallAtlas: TextureAtlas,
    private val vicinity: RenderingVicinity
) {
    fun createActor(x: Int, y: Int): WallActor =
        WallActor(
            wallAtlas.regionForWall(vicinity.wallAt(x, y)),
            x,
            y
        )

    private fun TextureAtlas.regionForWall(
        wallType: WallType
    ): TextureRegion =
        findRegion(wallType.name)
}
