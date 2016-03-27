package org.tendiwa.frontend.gdx2d.input

import com.badlogic.gdx.Input
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertFalse

class KeyCombinationPoolTest {
    @Test
    fun `stores key combinations once they are used`() {
        val pool = KeyCombinationPool()
        val firstAccess =
            pool.combination(keycode = Input.Keys.P, ctrl = true)
        val secondAccess =
            pool.combination(keycode = Input.Keys.P, ctrl = true)
        Assert.assertSame(firstAccess, secondAccess)
    }

    @Test
    fun `can obtain combination with multiple modifiers`() {
        KeyCombinationPool()
            .combination(Input.Keys.P, ctrl = true, alt = true)
            .apply {
                assert(this.ctrl)
                assert(this.alt)
                assertFalse(this.shift)
                assert(this.keycode == Input.Keys.P)
            }
    }
}
