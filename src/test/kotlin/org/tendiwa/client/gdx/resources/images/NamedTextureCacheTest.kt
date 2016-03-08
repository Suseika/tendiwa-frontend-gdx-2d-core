package org.tendiwa.client.gdx.resources.images

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.tendiwa.client.gdx.testing.LwjglGdxRule
import org.tendiwa.tools.expectIllegalArgument

class NamedTextureCacheTest {
    @JvmField @Rule val gdx = LwjglGdxRule()
    @JvmField @Rule val expectRule = ExpectedException.none()

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

    @Test
    fun `fails if there is no textures with the given name`() {
        expectRule.expectIllegalArgument("No texture for dudeman")
        NamedTextureCache(
            TextureAtlas(
                Gdx.files.classpath("atlas/example.atlas")
            )
        )
            .texture("dudeman", 0)
    }
}
