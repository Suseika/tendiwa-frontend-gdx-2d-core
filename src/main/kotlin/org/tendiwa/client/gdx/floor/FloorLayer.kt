package org.tendiwa.client.gdx.floor

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import org.tendiwa.client.gdx.resources.images.NamedTextureCache
import org.tendiwa.frontend.generic.RenderingVicinity

class FloorLayer(
    val textureCache: NamedTextureCache,
    val vicinity: RenderingVicinity
) : Actor() {
    override fun draw(batch: Batch, parentAlpha: Float) {
        vicinity.tileBounds.forEachTile {
            x, y ->
            if (vicinity.fieldOfView.contains(x, y)) {
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

    private fun textureAt(x: Int, y: Int): TextureRegion =
        textureCache.texture(
            name = "floors/" + vicinity.floorAt(x, y).name,
            index = 0
        )
}

