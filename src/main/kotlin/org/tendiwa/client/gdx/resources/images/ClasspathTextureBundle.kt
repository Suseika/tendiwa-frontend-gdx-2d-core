package org.tendiwa.client.gdx.resources.images

import org.apache.commons.io.FileUtils
import org.reflections.Reflections
import org.reflections.scanners.ResourcesScanner
import java.io.File
import java.math.BigInteger
import java.nio.file.Path
import java.security.MessageDigest
import java.util.regex.Pattern

class ClasspathTextureBundle(
    private val classpathPackages: List<String>
) {
    private val imageResourceNames: List<String> =
        classpathPackages
            .flatMap {
                Reflections(it, ResourcesScanner())
                    .getResources(Pattern.compile(".*\\.png"))
            }
            .sorted()

    val hash: String =
        MessageDigest
            .getInstance("SHA-256")
            .apply {
                update(
                    imageResourceNames
                        .joinToString { it }
                        .toByteArray(Charsets.UTF_8)
                )
            }
            .asHexadecimal()

    private fun MessageDigest.asHexadecimal(): String =
        String.format("%064x", BigInteger(1, digest()))

    fun extractTo(destination: Path) {
        imageResourceNames
            .forEach {
                FileUtils.copyURLToFile(
                    this.javaClass.getResource("/$it"),
                    fileForResource(it, destination)
                )
            }
    }

    private fun fileForResource(resource: String, destination: Path): File {
        val file = File(
            destination.toAbsolutePath().toString()
                + "/"
                + File(resource).toPath().toString()
        )
        return file
    }
}


