package org.tendiwa.frontend.gdx2d.walls

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.junit.Rule
import org.junit.Test
import org.tendiwa.backend.GridParallelepiped
import org.tendiwa.backend.by
import org.tendiwa.backend.space.Space
import org.tendiwa.backend.space.Voxel
import org.tendiwa.frontend.gdx2d.resources.images.NamedTextureCache
import org.tendiwa.frontend.gdx2d.testing.LwjglGdxRule
import org.tendiwa.frontend.generic.RenderingVicinity
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.grid.tiles.Tile
import kotlin.test.assertNotNull

class WallActorFactoryTest {
    @JvmField @Rule val gdx = LwjglGdxRule()

    @Test
    fun `creates actor if there is a wall at given coordinate`() {
        WallActorFactory(
            createMockCache(),
            createMockVicinity()
        )
            .createActor(Tile(1, 1))
            .let { assertNotNull(it) }
    }

    @Test
    fun `doesnt create actor if there is no wall at given coordinate`() {
        WallActorFactory(createMockCache(), createMockVicinity())
            .createActor(Tile(100, 100))
    }

    private fun createMockVicinity(): RenderingVicinity =
        RenderingVicinity(
            Space(
                GridParallelepiped(Voxel(0, 0, 0), 32 by 32 by 1)
            ),
            32 by 32,
            0
        )


    private fun createMockCache(): NamedTextureCache =
        NamedTextureCache(
            TextureAtlas().apply {
                addRegion("dude", TextureRegion(Texture("floors/stone_0.png")))
            }
        )

}
