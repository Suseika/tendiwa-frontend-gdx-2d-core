package org.tendiwa.client.gdx

import org.tendiwa.backend.space.floors.FloorType
import org.tendiwa.backend.space.walls.WallType
import org.tendiwa.plane.grid.rectangles.GridRectangle

interface RenderingVicinity {
    fun floorAt(x: Int, y: Int): FloorType
    fun wallAt(x: Int, y: Int): WallType
    var tileBounds: GridRectangle
}
