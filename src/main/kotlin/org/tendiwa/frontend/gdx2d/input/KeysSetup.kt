package org.tendiwa.frontend.gdx2d.input

import java.util.*

class KeysSetup() {
    private val keysToActions: MutableMap<Int, () -> Unit> =
        LinkedHashMap()

    fun addAction(keycode: Int, action: () -> Unit) {
        keysToActions.put(keycode, action)
    }

    fun actionAt(key: Int): (() -> Unit)? =
        keysToActions[key]
}
