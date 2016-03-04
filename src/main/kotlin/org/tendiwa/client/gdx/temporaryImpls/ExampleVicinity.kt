package org.tendiwa.client.gdx.temporaryImpls

import org.tendiwa.client.gdx.RenderingVicinity
import org.tendiwa.plane.grid.masks.StringGridMask
import org.tendiwa.world.floors.FloorType

class ExampleVicinity : RenderingVicinity {
    val mask = StringGridMask(
        "...................",
        "................#..",
        "...............##..",
        "..###...........#..",
        "..###..........##..",
        "..###...........#..",
        "....#....#.........",
        "........##.........",
        "...................",
        "...................",
        "......#............",
        "....#.......#####..",
        "...#...............",
        "...#...............",
        "...##.............."
    )
    override var tileBounds = mask.hull
    private val grass = FloorType("grass", false)
    private val stone = FloorType("stone", false)
    override fun floorAt(x: Int, y: Int): FloorType =
        if (mask.contains(x, y)) grass else stone
}
