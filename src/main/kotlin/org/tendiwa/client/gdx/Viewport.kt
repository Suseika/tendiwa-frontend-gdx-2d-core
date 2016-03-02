package org.tendiwa.client.gdx

import org.tendiwa.plane.grid.rectangles.GridRectangle

class Viewport(val tileBounds: GridRectangle) {
    companion object {
        val TILE_SIZE = 32
    }

    val pixelBounds =
        GridRectangle(
            tileBounds.x * TILE_SIZE,
            tileBounds.y * TILE_SIZE,
            tileBounds.width * TILE_SIZE,
            tileBounds.height * TILE_SIZE
        )
}
