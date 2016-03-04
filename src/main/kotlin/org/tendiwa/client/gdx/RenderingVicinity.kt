package org.tendiwa.client.gdx

import org.tendiwa.plane.grid.rectangles.GridRectangle
import org.tendiwa.world.floors.FloorType

interface RenderingVicinity {
    fun floorAt(x: Int, y: Int): FloorType
    var tileBounds: GridRectangle
}
