package org.tendiwa.frontend.gdx2d.resources.images

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
    private val imageResourceNames: List<TextureResource> =
        classpathPackages
            .flatMap { pkg ->
                Reflections(pkg, ResourcesScanner())
                    .getResources(Pattern.compile(".*\\.png"))
                    .map { TextureResource(it, pkg) }
            }
            .sortedBy { it.resource }

    private class TextureResource(val resource: String, pkg: String) {
        val shortName: String = resource.substring(pkg.length + 1)
    }

    val hash: String =
        MessageDigest
            .getInstance("SHA-256")
            .apply {
                update(
                    imageResourceNames
                        .joinToString { it.resource }
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
                    this.javaClass.getResource("/${it.resource}"),
                    fileForResource(it.shortName, destination)
                )
            }
    }

    private fun fileForResource(resource: String, destination: Path): File =
        File(destination.toAbsolutePath().toString() + "/$resource")
}


