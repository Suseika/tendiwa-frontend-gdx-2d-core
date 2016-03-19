package org.tendiwa.frontend.gdx2d

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import org.tendiwa.plane.grid.tiles.Tile

class TendiwaCamera : OrthographicCamera(
    Gdx.graphics.width.toFloat() / 32,
    Gdx.graphics.height.toFloat() / 32
) {
    init {
        position.set(
            viewportWidth / 2f,
            viewportHeight / 2f,
            0.0f
        )
        zoom = 1.0f
        update()
    }
}

fun TendiwaCamera.centerOnTile(targetTile: Tile) {
    position.set(
        targetTile.x.toFloat() + 0.5f,
        targetTile.y.toFloat() + 0.5f,
        0f
    )
}
