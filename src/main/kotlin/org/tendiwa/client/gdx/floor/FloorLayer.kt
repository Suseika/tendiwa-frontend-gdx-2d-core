package org.tendiwa.client.gdx.floor

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import org.tendiwa.client.gdx.Viewport

class FloorLayer(
    val viewport: Viewport,
    val textureAt: (Int, Int) -> Texture
) {
    fun draw(batch: Batch) {
        viewport.tileBounds.forEachTile {
            x, y ->
            batch.draw(
                textureAt(x, y),
                x.toFloat(),
                y.toFloat(),
                1f,
                1f
            )
        }
    }
}

