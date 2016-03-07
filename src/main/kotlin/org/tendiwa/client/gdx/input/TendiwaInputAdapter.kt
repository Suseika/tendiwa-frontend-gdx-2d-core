package org.tendiwa.client.gdx.input

import com.badlogic.gdx.InputAdapter

class TendiwaInputAdapter() : InputAdapter() {
    internal val keysSetup = KeysSetup()

    override fun keyDown(keycode: Int): Boolean {
        val action = keysSetup.actionAt(keycode)
        if (action == null) {
            return false
        } else {
            action()
            return true
        }
    }
}
