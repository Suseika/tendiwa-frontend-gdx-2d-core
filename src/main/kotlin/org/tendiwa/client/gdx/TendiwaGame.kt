package org.tendiwa.client.gdx

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import com.badlogic.gdx.utils.viewport.FitViewport
import org.tendiwa.backend.space.Reality
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.client.gdx.floor.FloorLayer
import org.tendiwa.client.gdx.input.TendiwaInputAdapter
import org.tendiwa.client.gdx.realThings.RealThingActorRegistry
import org.tendiwa.client.gdx.resources.images.NamedTextureCache
import org.tendiwa.client.gdx.walls.WallActorFactory
import org.tendiwa.frontend.generic.PlayerVolition
import org.tendiwa.frontend.generic.RenderingVicinity
import org.tendiwa.frontend.generic.hasWallAt
import org.tendiwa.plane.grid.dimensions.GridDimension
import org.tendiwa.stimuli.StimulusMedium

class TendiwaGame(
    private val atlasPath: String,
    private val reality: Reality,
    private val playerVolition: PlayerVolition,
    private val stimulusMedium: StimulusMedium,
    private val plugins: List<TendiwaGdxClientPlugin>
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
        val realThingActorRegistry = RealThingActorRegistry()
            .apply {
                vicinity.things.forEach {
                    addRealThing(it)
                }
            }
        stage = Stage(
            FitViewport(
                Gdx.graphics.width.toFloat() / 32,
                Gdx.graphics.height.toFloat() / 32,
                camera
            ),
            SpriteBatch()

        )
            .apply {
                actionsRequestRendering = false
                addActor(
                    FloorLayer(textureCache, vicinity)
                )
                vicinity.tileBounds.forEachTile { x, y ->
                    if (vicinity.hasWallAt(x, y)) {
                        wallActorFactory.createActor(x, y)
                            .let { addActor(it) }
                    }
                }
                realThingActorRegistry
                    .actors()
                    .forEach { addActor(it) }
            }
        stimulusMedium.subscribeToAll {
            if (it is Position.Change) {
                val actor = realThingActorRegistry
                    .actorOf(it.host)
                actor
                    .addAction(
                        MoveToAction()
                            .apply {
                                this.setPosition(
                                    it.to.x.toFloat(),
                                    it.to.y.toFloat()
                                )
                                this.duration = 0.1f
                            }
                    )
            }
        }
        val inputAdapter = TendiwaInputAdapter()
        Gdx.input.inputProcessor = inputAdapter
        plugins.forEach {
            it.init(
                camera,
                vicinity,
                playerVolition,
                inputAdapter.keysSetup,
                reality
            )
        }
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
    }
}

