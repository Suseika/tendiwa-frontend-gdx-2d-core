package org.tendiwa.frontend.gdx2d.input

import com.badlogic.gdx.Input

fun Input.isCtrlPressed(): Boolean =
    isKeyPressed(Input.Keys.CONTROL_LEFT)
        || isKeyPressed(Input.Keys.CONTROL_RIGHT)

fun Input.isShiftPressed(): Boolean =
    isKeyPressed(Input.Keys.SHIFT_LEFT)
        || isKeyPressed(Input.Keys.SHIFT_RIGHT)

fun Input.isAltPressed(): Boolean =
    isKeyPressed(Input.Keys.ALT_LEFT)
        || isKeyPressed(Input.Keys.ALT_RIGHT)

