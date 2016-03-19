package org.tendiwa.frontend.gdx2d.resources.images

import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.rules.TemporaryFolder
import org.tendiwa.frontend.gdx2d.testing.LwjglGdxRule
import org.tendiwa.tools.expectIllegalArgument

class TextureAtlasCacheTest {
    @JvmField @Rule val gdx = LwjglGdxRule()
    @JvmField @Rule val temp = TemporaryFolder()
    @JvmField @Rule val expectRule = ExpectedException.none()

    @Test
    fun `generates cache`() {
        TextureAtlasCache(
            temp.newFolder().toPath(),
            ClasspathTextureBundle(
                listOf("floors")
            )
        )
            .obtainAtlas()
    }

    @Test
    fun `rebuilds invalidated cache`() {
        val cacheDirectory = temp.newFolder().toPath()
        TextureAtlasCache(
            cacheDirectory,
            ClasspathTextureBundle(
                listOf("floors")
            )
        )
            .regenerate()
        TextureAtlasCache(
            cacheDirectory,
            ClasspathTextureBundle(
                listOf("walls")
            )
        )
            .obtainAtlas()
        assert(cacheDirectory.resolve("floors").toFile().exists().not())
        assert(cacheDirectory.resolve("walls").toFile().exists())
    }

    @Test
    fun `sees valid cache`() {
        val cacheDirectory = temp.newFolder().toPath()
        TextureAtlasCache(
            cacheDirectory,
            ClasspathTextureBundle(
                listOf("floors")
            )
        )
            .regenerate()
        TextureAtlasCache(
            cacheDirectory,
            ClasspathTextureBundle(
                listOf("floors")
            )
        )
            .isCacheValid()
    }

    @Test
    fun `fails if path is not a directory`() {
        expectRule.expectIllegalArgument("is not a directory")
        val notDirectory = temp.newFile().toPath()
        TextureAtlasCache(
            notDirectory,
            ClasspathTextureBundle(
                listOf("floors")
            )
        )
    }
}
