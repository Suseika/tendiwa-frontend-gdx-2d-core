package org.tendiwa.client.gdx

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.OrthographicCamera

class CameraInputAdapter(
    private val camera: OrthographicCamera
): InputAdapter() {
    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.LEFT) {
            camera.position.add(-1f, 0f, 0f)
            return true
        } else if (keycode == Input.Keys.RIGHT) {
            camera.position.add(1f, 0f, 0f)
        } else if (keycode == Input.Keys.UP) {
            camera.position.add(0f, 1f, 0f)
        } else if (keycode == Input.Keys.DOWN) {
            camera.position.add(0f, -1f, 0f)
        }
        return false
    }
}
