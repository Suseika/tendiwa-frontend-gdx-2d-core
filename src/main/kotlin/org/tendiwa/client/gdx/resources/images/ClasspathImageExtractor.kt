package org.tendiwa.client.gdx.resources.images

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
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
    Runtime.getRuntime().exec("mkdir $outputDirectory")
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

