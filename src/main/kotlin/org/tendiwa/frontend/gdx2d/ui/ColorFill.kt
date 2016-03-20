package org.tendiwa.frontend.gdx2d.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Skin

/**
 * Returns a 1×1 pixel [Image] of the given color.
 */
class ColorFill(color: Color) : Image(skin.newDrawable("white", color)) {
    private companion object {
        val skin = Skin().apply {
            add(
                "white",
                Texture(
                    Pixmap(1, 1, Pixmap.Format.RGBA8888)
                        .apply {
                            setColor(1f, 1f, 1f, 1f)
                            fill()
                        }
                )
            )
        }
    }
}


