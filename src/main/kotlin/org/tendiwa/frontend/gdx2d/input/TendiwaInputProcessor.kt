package org.tendiwa.frontend.gdx2d.input

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.scenes.scene2d.Stage

class TendiwaInputProcessor internal constructor(
    private val ui: Stage,
    private val keyCombinationPool: KeyCombinationPool,
    private val input: Input
) : InputProcessor {

    var uiMode: UiMode? = null

    override fun keyDown(keycode: Int): Boolean =
        tryCalling(
            getAction = {
                val combination =
                    keyCombinationPool.currentCombination(keycode, input)
                uiMode!!.keyboardActions[combination]
            },
            callAction = { it() }
        )

    private inline fun <T> tryCalling(
        getAction: () -> T?,
        callAction: (T) -> Unit
    ): Boolean {
        if (uiMode == null) {
            return false
        }
        val action = getAction()
        if (action == null) {
            return false
        } else {
            callAction(action)
            return true
        }
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchDown(
        screenX: Int,
        screenY: Int,
        pointer: Int,
        button: Int
    ): Boolean {
        if (uiHandlesClick(button, pointer, screenX, screenY)) {
            return true
        }
        return tryCalling(
            getAction = { uiMode!!.mouseActions[button] },
            callAction = { it(screenX, screenY) }
        )
    }

    private fun uiHandlesClick(
        button: Int,
        pointer: Int,
        screenX: Int,
        screenY: Int
    ) =
        ui.touchDown(screenX, screenY, pointer, button)

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean =
        tryCalling(
            getAction = { uiMode!!.mouseMoveAction },
            callAction = { it(screenX, screenY) }
        )

    override fun scrolled(amount: Int): Boolean {
        return false
    }

}
