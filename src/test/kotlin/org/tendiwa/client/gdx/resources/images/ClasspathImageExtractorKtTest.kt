package org.tendiwa.client.gdx.resources.images

import org.junit.Test
import org.tendiwa.client.gdx.testing.HeadlessGdxTest
import java.nio.file.Files

class ClasspathImageExtractorKtTest : HeadlessGdxTest() {
    @Test
    fun `extracts resources from classpath do a directory`() {
        val outputDirectory = Files.createTempDirectory("extracted")
        application.extractImagesFromClasspath(
            listOf("floors"),
            outputDirectory
        )
        try {
            assert(
                outputDirectory.toFile().listFiles()
                    .all { it.name.contains("png") }
            )
        } finally {
            outputDirectory.toFile().delete()
        }
    }
}
