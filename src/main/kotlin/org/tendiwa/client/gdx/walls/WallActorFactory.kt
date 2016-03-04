package org.tendiwa.client.gdx.walls

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.tendiwa.client.gdx.RenderingVicinity
import org.tendiwa.client.gdx.resources.images.NamedTextureCache
import org.tendiwa.world.WallType

class WallActorFactory(
    private val textureCache: NamedTextureCache,
    private val vicinity: RenderingVicinity
) {
    fun createActor(x: Int, y: Int): WallActor? =
        regionForWall(x, y)
            ?.let { WallActor(it, x, y) }

    private fun regionForWall(x: Int, y: Int): TextureRegion? {
        val wallType = vicinity.wallAt(x, y)
        return if (wallType == WallType.void) {
            null
        } else {
            textureCache.texture(
                wallType.name,
                wallVariantIndex(x, y)
            )
        }
    }

    /**
     * Returns the index of a texture variant for a wall. Variant indices are
     * assigned as follows:
     *
     * 1. Texture variant images have binary indices from 0000 to 1111;
     * 2. [com.badlogic.gdx.tools.texturepacker.TexturePacker] generates atlases
     * with indices with removed leading zeroes for those textures. That
     * preserves the sorting of the indices assigned in step 1;
     * 3. [com.badlogic.gdx.graphics.g2d.TextureAtlas] sorts the resulting
     * indices and maps them to indices from 0 to 15, thus every wall texture
     * should have 16 variants.
     */
    private fun wallVariantIndex(x: Int, y: Int) =
        vicinity.hasWallAt(x, y + 1) * 8 +
            vicinity.hasWallAt(x + 1, y) * 4 +
            vicinity.hasWallAt(x, y - 1) * 2 +
            vicinity.hasWallAt(x - 1, y)

    /**
     * Returns 1 if there is a wall, or 0 if there isn't.
     */
    private fun RenderingVicinity.hasWallAt(x: Int, y: Int): Int =
        if (wallAt(x, y) == WallType.void) 0 else 1
}
