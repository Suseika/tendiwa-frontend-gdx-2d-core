package org.tendiwa.client.gdx.walls

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor

class WallActor(
    private val region: TextureRegion,
    x: Int,
    y: Int
) : Actor() {
    init {
        setPosition(x.toFloat(), y.toFloat())
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.draw(
            region,
            this.x,
            this.y,
            region.regionWidth.toFloat() / 32,
            region.regionHeight.toFloat() / 32
        )
    }
}
