package org.tendiwa.client.gdx

import org.tendiwa.client.gdx.testing.HeadlessGdxTest

object GdxUtils {
    fun initializeGdx() {
        object : HeadlessGdxTest() {}
    }
}
