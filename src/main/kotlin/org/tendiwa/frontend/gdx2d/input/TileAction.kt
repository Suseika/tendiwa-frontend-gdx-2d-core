package org.tendiwa.frontend.gdx2d.input

interface TileAction {
    val localizationId: String
    operator fun invoke(x: Int, y: Int)
}
