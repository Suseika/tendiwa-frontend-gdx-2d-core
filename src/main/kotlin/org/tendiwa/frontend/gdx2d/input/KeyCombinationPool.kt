package org.tendiwa.frontend.gdx2d.input

import com.badlogic.gdx.Input
import java.util.*


class KeyCombinationPool {
    companion object Modifiers {
        val ctrl = 1 shl 8
        val alt = 1 shl 9
        val shift = 1 shl 10
    }

    private var combinations: MutableMap<Int, KeyCombination> = HashMap()

    fun combination(
        keycode: Int,
        ctrl: Boolean = false,
        alt: Boolean = false,
        shift: Boolean = false
    ): KeyCombination {
        val compositeKeyCode = computeCompositeKeyCode(keycode, ctrl, alt, shift)
        if (combinations.containsKey(compositeKeyCode)) {
            return combinations[compositeKeyCode]!!
        } else {
            val answer = KeyCombination(keycode, ctrl, alt, shift)
            combinations.put(compositeKeyCode, answer)
            return answer
        }
    }

    private fun computeCompositeKeyCode(
        keycode: Int,
        ctrl: Boolean,
        alt: Boolean,
        shift: Boolean
    ): Int =
        keycode
            .plus(if (ctrl) Modifiers.ctrl else 0)
            .plus(if (alt) Modifiers.alt else 0)
            .plus(if (shift) Modifiers.shift else 0)
}

/**
 * Returns the [KeyCombination] received in the current frame.
 */
fun KeyCombinationPool.currentCombination(
    keycode: Int,
    gdxInput: Input
): KeyCombination =
    combination(
        keycode = keycode,
        ctrl = gdxInput.isCtrlPressed(),
        alt = gdxInput.isAltPressed(),
        shift = gdxInput.isShiftPressed()
    )

