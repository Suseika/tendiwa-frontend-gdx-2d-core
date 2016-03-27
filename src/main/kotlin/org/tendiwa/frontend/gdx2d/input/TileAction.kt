package org.tendiwa.frontend.gdx2d.input

abstract class TileAction(
    val localizationId: String
) {
    operator abstract fun invoke(x: Int, y: Int)
}
