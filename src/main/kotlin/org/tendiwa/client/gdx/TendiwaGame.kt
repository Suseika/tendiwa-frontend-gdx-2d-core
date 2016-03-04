package org.tendiwa.client.gdx

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import org.tendiwa.client.gdx.floor.FloorLayer
import org.tendiwa.client.gdx.resources.images.NamedTextureCache
import org.tendiwa.client.gdx.walls.WallActorFactory

class TendiwaGame(
    private val atlasPath: String,
    private val createInputProcessor: (OrthographicCamera) -> InputProcessor,
    private val vicinity: RenderingVicinity
) : ApplicationAdapter() {
    lateinit var textureCache: NamedTextureCache
    lateinit var stage: Stage

    override fun create() {
        textureCache =
            NamedTextureCache(
                TextureAtlas(
                    Gdx.files.classpath(atlasPath)
                )
            )
        val camera = TendiwaCamera()
        val wallActorFactory =
        WallActorFactory(
            TextureAtlas(Gdx.files.classpath("walls/walls.atlas")),
            vicinity
        )
        stage = Stage(
            FitViewport(
                Gdx.graphics.width.toFloat()/32,
                Gdx.graphics.height.toFloat()/32,
                camera
            ),
            SpriteBatch()
        )
        .apply {
            addActor(
                FloorLayer(textureCache, vicinity)
            )
            vicinity.tileBounds.forEachTile { x, y ->
                addActor(
                    wallActorFactory.createActor(x, y)
                )
            }
        }
        Gdx.input.inputProcessor = createInputProcessor(camera)
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw()
    }
}
