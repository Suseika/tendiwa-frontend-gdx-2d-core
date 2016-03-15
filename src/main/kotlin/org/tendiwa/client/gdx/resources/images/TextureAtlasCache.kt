package org.tendiwa.client.gdx.resources.images

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.tools.texturepacker.TexturePacker
import java.io.PrintWriter
import java.nio.file.Files
import java.nio.file.Path

class TextureAtlasCache(
    private val directory: Path,
    private val textureBundle: ClasspathTextureBundle
) {
    companion object {
        private val HASH_FILE_NAME = "hash"
    }

    fun obtainAtlas(): TextureAtlas {
        if (directory.toFile().exists()) {
            if (!directory.toFile().isDirectory) {
                throw IllegalArgumentException(
                    "Path for cache directory $directory is not a directory"
                )
            }
            if (!isCacheValid()) {
                regenerate()
            }
        } else {
            regenerate()
        }
        return TextureAtlas(packFile())
    }

    internal fun isCacheValid(): Boolean {
        val hashFile = directory.resolve(HASH_FILE_NAME)
        return if (hashFile.toFile().isDirectory || !hashFile.toFile().exists()) {
            false
        } else {
            storedHash(hashFile) == textureBundle.hash
        }
    }

    private fun packFile(): FileHandle? {
        if (Gdx.files == null) {
            throw IllegalStateException(
                "Libgdx has not been initialized yet"
            )
        }
        return Gdx.files.absolute(
            directory
                .resolve("atlas")
                .resolve("universal.atlas")
                .toAbsolutePath()
                .toString()
        )
    }

    private fun storedHash(hashFile: Path): String =
        String(
            Files.readAllBytes(hashFile),
            Charsets.UTF_8
        )


    fun regenerate() {
        directory.toFile().mkdirs()
        writeHash(textureBundle.hash)
        val extractDirectory = directory.resolve("extracted")
        textureBundle.extractTo(extractDirectory)
        TexturePacker.process(
            extractDirectory.toString(),
            directory.resolve("atlas").toString(),
            "universal"
        )
        extractDirectory.toFile().deleteRecursively()
    }


    private fun writeHash(hash: String) {
        PrintWriter(directory.resolve(HASH_FILE_NAME).toFile())
            .use { it.write(hash) }
    }
}
