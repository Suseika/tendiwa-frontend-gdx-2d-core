package org.tendiwa.client.gdx.temporaryImpls

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import org.tendiwa.client.gdx.RenderingVicinity
import org.tendiwa.plane.grid.rectangles.GridRectangle

class CameraInputAdapter(
    private val camera: OrthographicCamera,
    private val vicinity: RenderingVicinity
) : InputAdapter() {
    override fun keyDown(keycode: Int): Boolean {
        var dx = 0
        var dy = 0
        if (keycode == Input.Keys.LEFT) {
            dx = -1
        } else if (keycode == Input.Keys.RIGHT) {
            dx = 1
        } else if (keycode == Input.Keys.UP) {
            dy = 1
        } else if (keycode == Input.Keys.DOWN) {
            dy = -1
        }
        if (dx != 0 || dy != 0) {
            camera.position.add(dx.toFloat(), dy.toFloat(), 0f)
            vicinity.tileBounds = vicinity.tileBounds.let {
                GridRectangle(
                    it.x + dx,
                    it.y + dy,
                    it.width,
                    it.height
                )
            }
            println(vicinity.tileBounds)
            return true
        }
        return false
    }
}
