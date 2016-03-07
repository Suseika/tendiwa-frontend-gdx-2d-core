package org.tendiwa.client.gdx.temporaryImpls

import com.badlogic.gdx.Gdx
import org.tendiwa.backend.space.Space
import org.tendiwa.backend.space.floors.FloorType
import org.tendiwa.backend.space.floors.floors
import org.tendiwa.backend.space.walls.WallType
import org.tendiwa.backend.space.walls.walls
import org.tendiwa.client.gdx.RenderingVicinity
import org.tendiwa.plane.grid.constructors.GridRectangle
import org.tendiwa.plane.grid.dimensions.by

class ExampleVicinity(private val space: Space) : RenderingVicinity {
    override var tileBounds = GridRectangle(
        Gdx.graphics.width / 32 by Gdx.graphics.height / 32
    )

    override fun floorAt(x: Int, y: Int): FloorType =
        space.floors.chunkWithTile(x, y).floorAt(x, y)

    override fun wallAt(x: Int, y: Int): WallType =
        space.walls.chunkWithTile(x, y).wallAt(x, y)
}
