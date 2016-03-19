package org.tendiwa.frontend.gdx2d.walls

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.tendiwa.backend.space.walls.WallType
import org.tendiwa.frontend.gdx2d.resources.images.NamedTextureCache
import org.tendiwa.frontend.generic.RenderingVicinity
import org.tendiwa.plane.grid.tiles.Tile

class WallActorFactory(
    private val textureCache: NamedTextureCache,
    private val vicinity: RenderingVicinity
) {
    fun createActor(tile: Tile): RegionActor =
        regionForWall(tile.x, tile.y)
            .let {
                /*
                 * Walls are implemented as [Actor]s instead of a single layer
                 * similar to [FloorLayer] because otherwise it would be very
                 * difficult to occlude things behind wall pieces and at the
                 * same time occlude wall pieces with things in front of the
                 * wall pieces.
                 */
                RegionActor(it)
                    .apply {
                        setPosition(tile.x.toFloat(), tile.y.toFloat())
                    }
            }

    private fun regionForWall(x: Int, y: Int): TextureRegion {
        val wallType = vicinity.wallAt(x, y)
        return if (wallType == WallType.void) {
            throw IllegalArgumentException("No wall at $x:$y")
        } else {
            textureCache.texture(
                "walls/" + wallType.name,
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
        vicinity.neighborBit(x, y + 1).shl(3) +
            vicinity.neighborBit(x + 1, y).shl(2) +
            vicinity.neighborBit(x, y - 1).shl(1) +
            vicinity.neighborBit(x - 1, y).shl(0)

    /**
     * Returns 1 if there is a wall, or 0 if there isn't.
     */
    private fun RenderingVicinity.neighborBit(x: Int, y: Int):
        Int =
        when {
            hasWall(x, y) -> 1
            else -> 0
        }

    private fun RenderingVicinity.hasWall(x: Int, y: Int) =
        vicinity.tileBounds.contains(x, y)
            && wallAt(x, y) != WallType.void
}
