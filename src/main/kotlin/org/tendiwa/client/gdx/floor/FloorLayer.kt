package org.tendiwa.client.gdx.floor

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled
import org.tendiwa.plane.grid.masks.BoundedGridMask

class FloorLayer(
    val tiles: BoundedGridMask
) {

    val renderer = ShapeRenderer()

    fun draw(camera: Camera) {
        renderer.projectionMatrix = camera.combined
        renderer.begin(Filled)
        tiles.hull.forEachTile {
            x, y ->
            drawTile(x, y, tiles.contains(x, y))
        }
        renderer.end()
    }

    fun drawTile(x: Int, y: Int, value: Boolean) {
        val color =
            if (value) Color.GREEN else Color.BLACK
        renderer.rect(
            x * 1.0f,
            y * 1.0f,
            1.0f,
            1.0f,
            color,
            color,
            color,
            color
        )
    }
}

