package org.tendiwa.client.gdx

import org.junit.Test
import org.tendiwa.plane.grid.rectangles.GridRectangle
import kotlin.test.assertEquals

class ViewportTest {
    @Test
    fun `pixel bounds`() {
        Viewport(
            GridRectangle(0, 0, 10, 10)
        )
            .pixelBounds
            .apply { assertEquals(this, GridRectangle(0, 0, 320, 320)) }
    }
}
