package org.tendiwa.client.gdx.testing

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.backends.headless.HeadlessApplication
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration
import org.junit.After

/**
 * Base test class for testing classes that require LibGDX to be initialized to
 * run properly.
 */
abstract class HeadlessGdxTest(
    applicationListener: ApplicationListener = object : ApplicationAdapter() {}
) {
    protected val application =
        HeadlessApplication(
            applicationListener,
            HeadlessApplicationConfiguration().apply {
                renderInterval = 1f / 60
            }
        )

    @After
    fun tearDown() {
        application.exit()
    }
}
