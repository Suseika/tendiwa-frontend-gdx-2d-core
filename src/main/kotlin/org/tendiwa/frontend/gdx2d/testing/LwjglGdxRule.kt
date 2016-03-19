package org.tendiwa.frontend.gdx2d.testing

import com.badlogic.gdx.Application
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.junit.rules.ExternalResource
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * Runs test methods in the LibGDX thread. This is required for properly calling
 * various OpenGL functions, since OpenGL context is only available in the
 * LibGDX managed thread.
 *
 * For an example of how calling OpenGL code outside the LibGDX thread
 * can mess up things, see [No OpenGL-Context on screen-swapping](http://badlogicgames.com/forum/viewtopic.php?f=11&t=9371).
 */
class LwjglGdxRule() : ExternalResource() {
    private lateinit var application: Application

    override fun before() {
        var lock = Object()
        synchronized(
            lock,
            {
                application = LwjglApplication(
                    object : ApplicationAdapter() {
                        override fun create() {
                            lock.notify()
                        }
                    },
                    LwjglApplicationConfiguration().apply {
                        resizable = false
                        width = 800
                        height = 600
                    }
                )
                lock.notify()
            }
        )
    }

    override fun apply(base: Statement, description: Description): Statement? =
        super.apply(
            object : Statement() {
                override fun evaluate() {
                    before()
                    runInLibgdxThread {
                        base.evaluate()
                    }
                    after()
                }
            },
            description
        )

    override fun after() {
        application.exit()
    }

    private fun runInLibgdxThread(
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
