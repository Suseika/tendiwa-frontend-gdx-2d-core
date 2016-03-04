package org.tendiwa.client.gdx

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import org.tendiwa.client.gdx.floor.FloorLayer
import org.tendiwa.client.gdx.resources.images.NamedTextureCache

class TendiwaGame(
    private val atlasPath: String,
    private val createInputProcessor: (OrthographicCamera) -> InputProcessor,
    private val vicinity: RenderingVicinity
) : ApplicationAdapter() {
    lateinit var layer: FloorLayer
    lateinit var batch: Batch
    lateinit var camera: OrthographicCamera
    lateinit var textureCache: NamedTextureCache

    override fun create() {
        camera = TendiwaCamera()
        batch =
            SpriteBatch()
        textureCache =
            NamedTextureCache(
                TextureAtlas(
                    Gdx.files.classpath(atlasPath)
                )
            )
        layer = FloorLayer(textureCache, vicinity)
        Gdx.input.inputProcessor = createInputProcessor(camera)
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
