package org.tendiwa.frontend.gdx2d

import org.tendiwa.frontend.gdx2d.testing.HeadlessGdxTest

object GdxUtils {
    fun initializeGdx() {
        object : HeadlessGdxTest() {}
    }
}
