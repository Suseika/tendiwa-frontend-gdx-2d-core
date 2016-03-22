package org.tendiwa.frontend.gdx2d.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table

fun Table.setBackgroundColor(color: Color) {
    background = ColorFill(color).drawable
}
