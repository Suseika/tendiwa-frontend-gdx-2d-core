package org.tendiwa.client.gdx.resources.images

import org.junit.Test
import org.tendiwa.client.gdx.testing.HeadlessGdxTest
import java.nio.file.Files

class TextureUtilsTest : HeadlessGdxTest() {
    @Test
    fun `extracts resources from classpath do a directory`() {
        val outputDirectory = Files.createTempDirectory("walls")
        TextureUtils.extractImagesFromClasspath(
            listOf("floors"),
            outputDirectory
        )
        assert(
            outputDirectory.toFile().listFiles()
                .all { it.name.contains("png") }
        )
    }

    @Test
    fun `builds atlas`() {
        val resourcesDir = Files.createTempDirectory("resources")
        TextureUtils.extractImagesFromClasspath(
            listOf("floors"),
            resourcesDir
        )
        val atlasDir = Files.createTempDirectory("atlas")
        TextureUtils.buildTextureAtlas(
            resourcesDir,
            atlasDir,
            "atlas"
        )
        assert(
            atlasDir.toFile().listFiles().all {
                it.name.contains("atlas")
            }
        )
    }
}
