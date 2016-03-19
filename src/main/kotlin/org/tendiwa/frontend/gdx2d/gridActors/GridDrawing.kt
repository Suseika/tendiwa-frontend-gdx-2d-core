package org.tendiwa.frontend.gdx2d.gridActors

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion

private val PIXEL_TILE_WIDTH = 32

fun Batch.drawInTileCoordinates(
    region: TextureRegion,
    x: Float,
    y: Float
) {
    draw(
        region,
        x,
        y,
        region.regionWidth.toFloat() / PIXEL_TILE_WIDTH,
        region.regionHeight.toFloat() / PIXEL_TILE_WIDTH
    )
}
