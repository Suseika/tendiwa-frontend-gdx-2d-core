package org.tendiwa.client.gdx.fonts

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import org.junit.Rule
import org.junit.Test
import org.tendiwa.client.gdx.testing.LwjglGdxRule
import kotlin.test.assertEquals

class FontsTest {
    @JvmField @Rule val gdx = LwjglGdxRule()

    @Test
    fun `generates font`() {
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
