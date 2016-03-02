package org.tendiwa.client.gdx.testing

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.backends.headless.HeadlessApplication
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration
import org.junit.After

/**
 * Base test class for testing classes that require LibGDX to be initialized to
 * run properly.
 */
abstract class HeadlessGdxTest {
    protected val application =
        HeadlessApplication(
            object : ApplicationAdapter() {},
            HeadlessApplicationConfiguration().apply
            {
                renderInterval = 1f / 60
            }
        )

    @After
    fun tearDown() {
        application.exit()
    }
}
