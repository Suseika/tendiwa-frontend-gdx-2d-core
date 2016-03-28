package org.tendiwa.frontend.gdx2d.input

import java.util.*

class UiMode(
    vararg keymappings: Pair<KeyCombination, UiAction>
) {

    private val _keyboardActions =
        LinkedHashMap<KeyCombination, UiAction>()
            .apply {
                keymappings.forEach {
                    this[it.first] = it.second
                }
            }

    val keyboardActions: Map<KeyCombination, UiAction>
        get() = _keyboardActions

    private val _mouseActions = LinkedHashMap<Int, TileAction>()

    val mouseActions: Map<Int, TileAction>
        get() = _mouseActions

    var mouseMoveAction: TileAction? = null

    /**
     * Maps a key combination to an action so action will be executed when that key combination is pressed.
     * @param combination An integer which is a sum of 4 parameters:  * Keycode (from [Input.Keys]) * `isCtrl ? 1 &lt;&lt; 8
     * : 0` * `isAlt ? 1 &lt;&lt; 9 : 0` * `isShift ? 1 &lt;&lt; 10 : 0`
     */
    operator fun set(combination: KeyCombination, action: UiAction) {
        if (_keyboardActions.containsKey(combination)) {
            throw IllegalArgumentException(
                "Mapping already assigned for combination $combination"
            )
        }
        _keyboardActions[combination] = action
    }

    operator fun set(button: Int, action: TileAction) {
        if (mouseActions.containsKey(button)) {
            throw IllegalArgumentException(
                "Mapping already assigned for button $button"
            )
        }
        _mouseActions[button] = action
    }
}
