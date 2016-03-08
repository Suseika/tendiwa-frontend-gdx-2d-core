package org.tendiwa.client.gdx.resources.images

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Array
import java.util.*

class NamedTextureCache(
    private val atlas: TextureAtlas
) {
    private val typesToRegions =
        HashMap<String, Array<TextureAtlas.AtlasRegion>>()

    fun texture(name: String, index: Int): TextureRegion =
        obtainRegions(name)[index]

    fun numberOfVariants(name: String): Int =
        obtainRegions(name).size

    private fun obtainRegions(name: String): Array<TextureAtlas.AtlasRegion> =
        typesToRegions
            .getOrPut(name, {
                val regions = atlas.findRegions(name)
                if (regions.size == 0) {
                    throw IllegalArgumentException("No texture for \"$name\"")
                }
                regions
            })
}
