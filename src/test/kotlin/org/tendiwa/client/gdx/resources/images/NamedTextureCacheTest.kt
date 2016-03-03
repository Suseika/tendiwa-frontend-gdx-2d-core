package org.tendiwa.client.gdx.resources.images

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import org.junit.Assert
import org.junit.Test
import org.tendiwa.client.gdx.testing.LwjglGdxTest

class NamedTextureCacheTest : LwjglGdxTest() {
    @Test
    fun `caches textures`() {
        runInLibgdxThread {
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
}
