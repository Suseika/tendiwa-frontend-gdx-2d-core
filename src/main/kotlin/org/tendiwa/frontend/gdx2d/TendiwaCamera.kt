package org.tendiwa.frontend.gdx2d

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3
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

    fun screenCoordinatesToTileCoordinates(x: Int, y: Int): Tile =
        this.unproject(Vector3(x.toFloat(), y.toFloat(), 0f))
            .let {
                it ->
                Tile(
                    Math.floor(it.x.toDouble()).toInt(),
                    Math.floor(it.y.toDouble()).toInt()
                )
            }
}

fun TendiwaCamera.centerOnTile(targetTile: Tile) {
    position.set(
        targetTile.x.toFloat() + 0.5f,
        targetTile.y.toFloat() + 0.5f,
        0f
    )
}
