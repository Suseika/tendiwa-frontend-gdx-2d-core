package org.tendiwa.frontend.gdx2d.input

import com.badlogic.gdx.Input
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class KeyCombinationTest {
    @JvmField @Rule val expectRule = ExpectedException.none()

    @Test
    fun `can handle more than one modifier`() {
        KeyCombination(
            Input.Keys.D,
            ctrl = true,
            alt = true
        )
            .apply {
                assert(ctrl)
                assert(alt)
                assertFalse(shift)
            }
    }

    @Test
    fun `toString() returns the name of the combination`() {
        assertEquals(
            "Ctrl + d",
            KeyCombination(
                Input.Keys.D,
                ctrl = true
            )
                .toString()
        )
    }

    @Test
    fun `shifted keys names are rendered as the corresponding characters`() {
        assertEquals(
            "+",
            KeyCombination(
                Input.Keys.EQUALS,
                shift = true
            )
                .toString()
        )
    }

    @Test
    fun `shifted letter names rendered as uppercase letters`() {
        assertEquals(
            "E",
            KeyCombination(
                Input.Keys.E,
                shift = true
            )
                .toString()
        )
    }

    @Test
    fun `non-shifted letter names rendered as lowercase letters`() {
        assertEquals(
            "e",
            KeyCombination(
                Input.Keys.E
            )
                .toString()
        )
    }
}
