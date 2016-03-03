package org.tendiwa.client.gdx

import org.tendiwa.world.floors.FloorType

/**
 *
 */
interface RenderingVicinity {
    fun floorAt(x: Int, y: Int): FloorType
    val viewport: Viewport
}
