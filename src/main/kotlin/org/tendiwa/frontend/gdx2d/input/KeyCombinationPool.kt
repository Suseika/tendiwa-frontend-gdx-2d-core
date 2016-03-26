package org.tendiwa.frontend.gdx2d.input

import java.util.*


class KeyCombinationPool {
    companion object Modifiers {
        val ctrl = 1 shl 8
        val alt = 1 shl 9
        val shift = 1 shl 10
    }

    private var combinations: MutableMap<Int, KeyCombination> = HashMap()

    fun obtainCombination(
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

    private fun obtainCombination(combination: Int): KeyCombination =
        obtainCombination(
            combination % Modifiers.ctrl,
            (combination and Modifiers.ctrl) == Modifiers.ctrl,
            (combination and Modifiers.alt) == Modifiers.alt,
            (combination and Modifiers.shift) == Modifiers.shift
        )
}

