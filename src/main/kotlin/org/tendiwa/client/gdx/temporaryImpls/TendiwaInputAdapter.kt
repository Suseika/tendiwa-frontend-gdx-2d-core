package org.tendiwa.client.gdx.temporaryImpls

import com.badlogic.gdx.InputAdapter
import org.tendiwa.client.gdx.input.KeysSetup

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
