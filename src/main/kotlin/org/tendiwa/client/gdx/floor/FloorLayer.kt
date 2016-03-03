package org.tendiwa.client.gdx.floor

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.tendiwa.client.gdx.RenderingVicinity
import org.tendiwa.client.gdx.resources.images.NamedTextureCache

class FloorLayer(
    val textureCache: NamedTextureCache,
    val vicinity: RenderingVicinity
) {
    fun draw(batch: Batch) {
        vicinity.viewport.tileBounds.forEachTile {
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

    private fun textureAt(x: Int, y: Int): TextureRegion =
        textureCache.texture(
            name = vicinity.floorAt(x, y).name,
            index = 0
        )
}

