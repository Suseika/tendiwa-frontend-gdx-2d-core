package org.tendiwa.client.gdx.fonts

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import org.junit.Test
import org.tendiwa.client.gdx.testing.LwjglGdxTest
import kotlin.test.assertEquals

class FontsTest : LwjglGdxTest() {
    @Test
    fun `generates font`() {
        runInLibgdxThread {
            Fonts.generateFont(
                Gdx.files.classpath("fonts/DejaVuSans.ttf"),
                {
                    size = 12
                    color = Color.BLACK
                    borderWidth = 1.0f
                }
            )
                .apply { assertEquals(Color.BLACK, this.color) }
        }
    }
}
