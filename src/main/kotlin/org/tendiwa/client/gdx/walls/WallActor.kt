package org.tendiwa.client.gdx.walls

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor

/**
 * Walls are implemented as [Actor]s instead of a single layer similar to
 * [FloorLayer] because otherwise it would be very difficult to occlude things
 * behind wall pieces and at the same time occlude wall pieces with things in
 * front of the wall pieces.
 */
// TODO: Reimplement wall drawing using a single actor per horizontal row.
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
