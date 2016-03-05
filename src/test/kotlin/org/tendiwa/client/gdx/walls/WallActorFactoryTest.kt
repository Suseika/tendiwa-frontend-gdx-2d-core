package org.tendiwa.client.gdx.walls

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.junit.Rule
import org.junit.Test
import org.tendiwa.client.gdx.RenderingVicinity
import org.tendiwa.client.gdx.resources.images.NamedTextureCache
import org.tendiwa.client.gdx.testing.LwjglGdxRule
import org.tendiwa.plane.grid.rectangles.GridRectangle
import org.tendiwa.world.walls.WallType
import org.tendiwa.world.floors.FloorType
import kotlin.test.assertNotNull

class WallActorFactoryTest {
    @JvmField @Rule val gdx = LwjglGdxRule()

    @Test
    fun `creates actor if there is a wall at given coordinate`() {
        WallActorFactory(createMockCache(), createMockVicinity())
            .createActor(1, 1)
            .let { assertNotNull(it) }
    }

    @Test
    fun `doesnt create actor if there is no wall at given coordinate`() {
        WallActorFactory(createMockCache(), createMockVicinity())
            .createActor(100, 100)
    }

    private fun createMockVicinity(): RenderingVicinity {
        return object : RenderingVicinity {
            override lateinit var tileBounds: GridRectangle

            val dudeWall = WallType("dude")
            override fun wallAt(x: Int, y: Int): WallType =
                if (x == 1 && y == 1)
                    dudeWall
                else
                    WallType.void

            override fun floorAt(x: Int, y: Int): FloorType {
                throw UnsupportedOperationException()
            }
        }
    }

    private fun createMockCache(): NamedTextureCache {
        return NamedTextureCache(
            TextureAtlas().apply {
                addRegion("dude", TextureRegion(Texture("floors/stone_0.png")))
            }
        )
    }

}
