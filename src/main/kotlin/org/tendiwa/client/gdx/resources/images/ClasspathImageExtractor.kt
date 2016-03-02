package org.tendiwa.client.gdx.resources.images

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.tools.texturepacker.TexturePacker
import org.reflections.Reflections
import org.reflections.scanners.ResourcesScanner
import java.io.File
import java.nio.file.Path
import java.util.regex.Pattern

/**
 * Copies image files from jars in classpath to current working directory.
 */
fun Application.extractImagesFromClasspath(
    classpathPackages: List<String>,
    outputDirectory: Path
) {
    classpathPackages.forEach {
        Reflections(it, ResourcesScanner())
            .getResources(Pattern.compile(".*\\.png"))
            .forEach {
                resource ->
                val file = Gdx.files.classpath(
                    resource.toString()
                )
                file.copyTo(
                    FileHandle(
                        File(
                            outputDirectory.toAbsolutePath().toString()
                                + "/"
                                + File (resource).toPath().fileName)
                    )
                )
            }
    }
}

/**
 * Combines all images in a directory into a texture atlas.
 * @param packFileName See [TexturePacker.process].
 */
fun Application.buildTextureAtlas(
    inputDirectory: Path,
    outputDirectory: Path,
    packFileName: String
) {
    TexturePacker.process(
        inputDirectory.toString(),
        outputDirectory.toString(),
        packFileName
    )
}

