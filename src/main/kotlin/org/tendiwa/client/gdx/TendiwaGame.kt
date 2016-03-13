package org.tendiwa.client.gdx

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import org.tendiwa.backend.existence.StimulusMedium
import org.tendiwa.backend.space.Reality
import org.tendiwa.backend.space.aspects.position
import org.tendiwa.client.gdx.floor.FloorLayer
import org.tendiwa.client.gdx.input.KeysSetup
import org.tendiwa.client.gdx.input.TendiwaInputAdapter
import org.tendiwa.client.gdx.resources.images.NamedTextureCache
import org.tendiwa.frontend.generic.PlayerVolition
import org.tendiwa.frontend.generic.RenderingVicinity
import org.tendiwa.frontend.generic.hasWallAt
import org.tendiwa.math.integers.divCover
import org.tendiwa.plane.grid.dimensions.GridDimension
import org.tendiwa.plane.grid.masks.contains
import org.tendiwa.plane.grid.tiles.Tile

class TendiwaGame(
    private val atlasPath: String,
    val reality: Reality,
    val playerVolition: PlayerVolition,
    private val stimulusMedium: StimulusMedium,
    private val plugins: List<TendiwaGdxClientPlugin>
) : ApplicationAdapter() {
    lateinit var textureCache: NamedTextureCache
    lateinit var stage: Stage
    lateinit var vicinity: RenderingVicinity
    lateinit var camera: TendiwaCamera
    lateinit var keysSetup: KeysSetup
    lateinit var frontendStimulusMedium: FrontendStimulusMedium
    lateinit var gridActorRegistry: GridActorRegistry

    override fun create() {
        initVicinity()
        initFacilities()
        initInput()
        initReactions()
        initPlugins()
        initSurroundings()
    }


    private fun initSurroundings() {
        stage.apply {
            actionsRequestRendering = false
            addActor(
                FloorLayer(textureCache, vicinity)
            )
            vicinity.tileBounds.forEachTile { x, y ->
                if (
                vicinity.hasWallAt(x, y) && vicinity.fieldOfView.contains(x, y)
                ) {
                    gridActorRegistry.spawnWall(Tile(x, y))
                }
            }
            vicinity.things.forEach {
                if (vicinity.fieldOfView.contains(it.position.tile)) {
                    gridActorRegistry.spawnRealThing(it)
                }
            }
        }
    }

    private fun initReactions() {
        frontendStimulusMedium = FrontendStimulusMedium()
        stimulusMedium.subscribeToAll(
            { frontendStimulusMedium.handleStimulus(it) }
        )
    }

    private fun initInput() {
        val inputAdapter = TendiwaInputAdapter()
        Gdx.input.inputProcessor = inputAdapter
        keysSetup = inputAdapter.keysSetup
    }

    private fun initFacilities() {
        textureCache =
            NamedTextureCache(
                TextureAtlas(
                    Gdx.files.classpath(atlasPath)
                )
            )
        camera = TendiwaCamera()
        stage = Stage(
            FitViewport(
                Gdx.graphics.width.toFloat() / 32,
                Gdx.graphics.height.toFloat() / 32,
                camera
            ),
            SpriteBatch()
        )
        gridActorRegistry = GridActorRegistry(vicinity, stage)
    }

    private fun initVicinity() {
        vicinity = RenderingVicinity(
            reality.space,
            GridDimension(
                Gdx.graphics.width divCover 32,
                Gdx.graphics.height divCover 32
            )
        )
    }

    private fun initPlugins() {
        plugins.forEach { it.init(this) }
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
    }
}

