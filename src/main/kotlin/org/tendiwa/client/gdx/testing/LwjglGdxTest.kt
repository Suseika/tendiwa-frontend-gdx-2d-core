package org.tendiwa.client.gdx.testing

import com.badlogic.gdx.Application
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.junit.After

/**
 * A base class for unit tests that need to use OpenGL capabilities of LibGDX.
 */
abstract class LwjglGdxTest(
    private val listener: ApplicationListener = object : ApplicationAdapter() {}
) {
    protected val application: Application =
        LwjglApplication(
            listener,
            LwjglApplicationConfiguration().apply {
                resizable = false
                width = 800
                height = 600
            }
        )

    @After
    fun tearDown() {
        application.exit()
    }

    /**
     * Runs a procedure in the LibGDX thread. This is required for properly
     * calling various OpenGL functions, since OpenGL context is only
     * available in the LibGDX managed thread.
     *
     * For an example of how calling OpenGL code outside the LibGDX thread
     * can mess up things, see [No OpenGL-Context on screen-swapping](http://badlogicgames.com/forum/viewtopic.php?f=11&t=9371).
     */
    fun runInLibgdxThread(
        action: () -> Unit
    ) {
        var lock = Object()
        synchronized(lock, {
            application.postRunnable {
                action()
                lock.notify()
            }
        })
    }
}
