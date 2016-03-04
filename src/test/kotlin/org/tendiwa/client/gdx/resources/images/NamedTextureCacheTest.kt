package org.tendiwa.client.gdx.resources.images

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.tendiwa.client.gdx.testing.LwjglGdxRule

class NamedTextureCacheTest {
    @JvmField @Rule val gdx = LwjglGdxRule()

    @Test
    fun `caches textures`() {
        NamedTextureCache(
            TextureAtlas(
                Gdx.files.classpath("atlas/example.atlas")
            )
        )
            .apply {
                Assert.assertSame(
                    texture("grass", 0),
                    texture("grass", 0)
                )
            }
    }
}
