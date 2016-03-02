package org.tendiwa.client.gdx

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.tendiwa.client.gdx.floor.FloorLayer
import org.tendiwa.plane.grid.masks.StringGridMask

class TendiwaGame : ApplicationAdapter() {
    lateinit var layer: FloorLayer
    lateinit var camera: Camera
    lateinit var batch: Batch

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
        layer =
            FloorLayer(
                StringGridMask(
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
            )
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update()
        batch.projectionMatrix = camera.combined
        batch.begin()
        layer.draw(camera)
        batch.end()
    }
}
