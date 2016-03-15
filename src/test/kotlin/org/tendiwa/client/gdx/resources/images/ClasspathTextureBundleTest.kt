package org.tendiwa.client.gdx.resources.images

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import kotlin.test.assertEquals

class ClasspathTextureBundleTest {
    @JvmField @Rule val temp = TemporaryFolder()

    @Test
    fun `computes hash of resource names`() {
        ClasspathTextureBundle(
            listOf("floors")
        )
            .hash
            .apply {
                assert(this.toCharArray().all { it in 'a'..'z' || it in '0'..'9' })
            }
    }

    @Test
    fun `extracts resources`() {
        val destination = temp.newFolder().toPath()
        ClasspathTextureBundle(
            listOf("floors")
        )
            .extractTo(destination)
        destination
            .resolve("floors")
            .toFile()
            .listFiles()
            .map { it.name }
            .toSet()
            .apply {
                assertEquals(
                    setOf(
                        "grass_1.png",
                        "stone_0.png",
                        "stone_1.png"
                    ),
                    this
                )
            }
    }
}
