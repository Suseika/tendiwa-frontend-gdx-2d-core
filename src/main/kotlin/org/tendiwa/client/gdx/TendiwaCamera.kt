package org.tendiwa.client.gdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera

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
