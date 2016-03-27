package org.tendiwa.frontend.gdx2d.input

interface UiAction {
    val localizationId: String

    operator fun invoke()
}
