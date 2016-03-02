package org.tendiwa.client.gdx

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.tendiwa.client.gdx.floor.FloorLayer
import org.tendiwa.plane.grid.masks.StringGridMask

class TendiwaGame(
) : ApplicationAdapter() {
    lateinit var layer: FloorLayer
    lateinit var camera: Camera
    lateinit var batch: Batch
    lateinit var assetManager: AssetManager

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
        val texturePath1 =
            "/home/suseika/Projects/tendiwa/game/frontend/gdx-2d/frontend-gdx-2d-core/src/test/resources/floors/grass_1.png"
        val texturePath2 =
            "/home/suseika/Projects/tendiwa/game/frontend/gdx-2d/frontend-gdx-2d-core/src/test/resources/floors/stone_1.png"
        assetManager =
            AssetManager(
                AbsoluteFileHandleResolver()
            )
                .apply {
                    load(texturePath1, Texture::class.java)
                    load(texturePath2, Texture::class.java)
                    finishLoading()
                }
        batch =
            SpriteBatch()
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
        layer =
            FloorLayer(
                Viewport(mask.hull),
                { x, y ->
                    assetManager.get(
                        if (mask.contains(x, y)) texturePath1 else texturePath2,
                        Texture::class.java
                    )
                }
            )
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
