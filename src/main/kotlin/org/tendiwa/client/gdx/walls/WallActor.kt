package org.tendiwa.client.gdx.walls

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor

class WallActor(
    private val region: TextureRegion,
    private val x: Int,
    private val y: Int
) : Actor() {
    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.draw(
            region,
            x.toFloat(),
            y.toFloat(),
            region.regionWidth.toFloat() / 32,
            region.regionHeight.toFloat() / 32
        )
    }
}
