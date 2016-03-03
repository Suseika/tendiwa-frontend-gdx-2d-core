package org.tendiwa.client.gdx

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import org.tendiwa.client.gdx.floor.FloorLayer
import org.tendiwa.client.gdx.resources.images.NamedTextureCache
import org.tendiwa.plane.grid.masks.StringGridMask
import org.tendiwa.world.floors.FloorType

class TendiwaGame(
    private val atlasPath: String
) : ApplicationAdapter() {
    lateinit var layer: FloorLayer
    lateinit var camera: Camera
    lateinit var batch: Batch
    lateinit var textureCache: NamedTextureCache
    val vicinity: RenderingVicinity
        get() = object : RenderingVicinity {
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
            override val viewport = Viewport(mask.hull)
            private val grass = FloorType("grass", false)
            private val stone = FloorType("stone", false)
            override fun floorAt(x: Int, y: Int): FloorType =
                if (mask.contains(x, y)) grass else stone
        }

    override fun create() {
        camera =
            OrthographicCamera(
                Gdx.graphics.width.toFloat() / 32,
                Gdx.graphics.height.toFloat() / 32
            )
                .apply {
                    position.set(
                        viewportWidth / 2f,
                        viewportHeight / 2f,
                        0.0f
                    )
                    zoom = 1.0f
                    update()
                }
        batch =
            SpriteBatch()
        textureCache =
            NamedTextureCache(
                TextureAtlas(
                    Gdx.files.classpath(atlasPath)
                )
            )
        layer = FloorLayer(textureCache, vicinity)
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update()
        batch.projectionMatrix = camera.combined
        batch.begin()
        layer.draw(batch)
        batch.end()
    }
}
