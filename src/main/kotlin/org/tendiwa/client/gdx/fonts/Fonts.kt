package org.tendiwa.client.gdx.fonts

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter

object Fonts {
    fun generateFont(
        file: FileHandle,
        parameters: FreeTypeFontParameter.() -> Unit
    ): BitmapFont =
        FreeTypeFontGenerator(file).generateFont(
            FreeTypeFontParameter().apply(parameters)
        )
}
