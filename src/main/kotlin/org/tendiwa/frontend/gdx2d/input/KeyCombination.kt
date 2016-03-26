package org.tendiwa.frontend.gdx2d.input

import com.badlogic.gdx.Input
import java.util.*

/**
 * Contains a keycode of the pressed key, and whether ctrl, shift of alt were pressed. Ctrl, Shift and Alt can't be
 * pressed at the same time. If two or more modifiers are pressed at the same time, then one is chosen by priority:
 * Shift > Atl > Ctrl.
 */
class KeyCombination(
    val keycode: Int,
    val ctrl: Boolean = false,
    val alt: Boolean = false,
    val shift: Boolean = false
) {

    private fun moreThanOneIsTrue(a: Boolean, b: Boolean, c: Boolean) =
        !(!b && !a && !c) && !(b xor a xor c)

    override fun toString(): String {
        val keyNameInLibgdx = Input.Keys.toString(keycode)
        val properKeyName: String
        val firstChar = keyNameInLibgdx[0]
        val isKeyAlphanumeric = keyNameInLibgdx.length == 1
        if (isKeyAlphanumeric) {
            if (firstChar >= 'A' && firstChar <= 'Z') {
                properKeyName =
                    if (shift) {
                        firstChar
                    } else {
                        Character.toLowerCase(firstChar)
                    }
                        .let { Character.toString(it) }
            } else {
                assert(alphanumericToShifted.containsKey(firstChar))
                properKeyName = Character.toString(
                    alphanumericToShifted[firstChar]!!
                )
            }
        } else {
            properKeyName = keyNameInLibgdx
        }
        val answer = keyCombinationName(isKeyAlphanumeric, properKeyName)
        return answer.toString()
    }

    private fun keyCombinationName(
        isKeyAlphanumeric: Boolean,
        properKeyName: String
    ): StringBuilder {
        val answer = StringBuilder()
        if (ctrl) {
            answer.append("Ctrl + ")
        }
        if (alt) {
            answer.append("Alt + ")
        }
        if (shift && !isKeyAlphanumeric) {
            answer.append("Shift + ")
        }
        answer.append(properKeyName)
        return answer
    }

    companion object {
        private val alphanumericToShifted = HashMap<Char, Char>()

        init {
            alphanumericToShifted.put(',', '<')
            alphanumericToShifted.put('.', '>')
            alphanumericToShifted.put('/', '?')
            alphanumericToShifted.put(';', ':')
            alphanumericToShifted.put('\'', '\"')
            alphanumericToShifted.put('[', '{')
            alphanumericToShifted.put(']', '}')
            alphanumericToShifted.put('1', '!')
            alphanumericToShifted.put('2', '@')
            alphanumericToShifted.put('3', '#')
            alphanumericToShifted.put('4', '$')
            alphanumericToShifted.put('5', '%')
            alphanumericToShifted.put('6', '^')
            alphanumericToShifted.put('7', '&')
            alphanumericToShifted.put('8', '*')
            alphanumericToShifted.put('9', '(')
            alphanumericToShifted.put('0', ')')
            alphanumericToShifted.put('-', '_')
            alphanumericToShifted.put('=', '+')
            alphanumericToShifted.put('`', '~')
            alphanumericToShifted.put('\\', '|')
        }
    }
}
