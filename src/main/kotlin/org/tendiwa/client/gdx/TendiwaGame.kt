package org.tendiwa.client.gdx

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import org.tendiwa.backend.space.Reality
import org.tendiwa.client.gdx.floor.FloorLayer
import org.tendiwa.client.gdx.resources.images.NamedTextureCache
import org.tendiwa.client.gdx.temporaryImpls.CameraInputAdapter
import org.tendiwa.client.gdx.walls.WallActorFactory
import org.tendiwa.frontend.generic.RenderingVicinity
import org.tendiwa.plane.grid.dimensions.GridDimension

class TendiwaGame(
    private val atlasPath: String,
    private val reality: Reality
) : ApplicationAdapter() {
    lateinit var textureCache: NamedTextureCache
    lateinit var stage: Stage
    lateinit var vicinity: RenderingVicinity

    override fun create() {
        vicinity = RenderingVicinity(
            reality.space,
            GridDimension(
                Gdx.graphics.width / 32,
                Gdx.graphics.height / 32
            )
        )
        textureCache =
            NamedTextureCache(
                TextureAtlas(
                    Gdx.files.classpath(atlasPath)
                )
            )
        val camera = TendiwaCamera()
        val wallActorFactory =
            WallActorFactory(
                NamedTextureCache(
                    TextureAtlas(Gdx.files.classpath("walls/walls.atlas"))
                ),
                vicinity
            )
        stage = Stage(
            FitViewport(
                Gdx.graphics.width.toFloat() / 32,
                Gdx.graphics.height.toFloat() / 32,
                camera
            ),
            SpriteBatch()
        )
            .apply {
                addActor(
                    FloorLayer(textureCache, vicinity)
                )
                vicinity.tileBounds.forEachTile { x, y ->
                    wallActorFactory.createActor(x, y)
                        ?.let { addActor(it) }
                }
            }
        Gdx.input.inputProcessor = CameraInputAdapter(camera, vicinity)
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw()
    }
}
