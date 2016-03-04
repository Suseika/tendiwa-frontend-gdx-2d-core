package org.tendiwa.client.gdx.temporaryImpls

import org.tendiwa.client.gdx.RenderingVicinity
import org.tendiwa.plane.grid.masks.StringGridMask
import org.tendiwa.world.WallType
import org.tendiwa.world.floors.FloorType

class ExampleVicinity : RenderingVicinity {
    val floorMask = StringGridMask(
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
    val wallMask = StringGridMask(
        "...................",
        "...................",
        "...................",
        "...................",
        "...................",
        "....############...",
        "....#..........#...",
        "....#..........#...",
        "....#..........#...",
        "....#....#.....#...",
        "....#..........#...",
        "....#..........#...",
        "....############...",
        "...................",
        "..................."
    )
    override var tileBounds = floorMask.hull
    private val grassFloor = FloorType("grass", false)
    private val stoneFloor = FloorType("stone", false)
    private val stoneWall = WallType("wall_gray_stone")
    private val voidWall = WallType.void

    override fun floorAt(x: Int, y: Int): FloorType =
        if (floorMask.contains(x, y)) grassFloor else stoneFloor

    override fun wallAt(x: Int, y: Int): WallType =
        if (wallMask.contains(x, y)) stoneWall else voidWall
}
