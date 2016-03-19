package org.tendiwa.client.gdx.walls

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import org.tendiwa.client.gdx.gridActors.drawInTileCoordinates

class RegionActor(
    private val region: TextureRegion
) : Actor() {
    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.drawInTileCoordinates(region, x, y)
    }
}
