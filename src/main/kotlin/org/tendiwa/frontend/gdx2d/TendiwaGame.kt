package org.tendiwa.frontend.gdx2d

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import org.tendiwa.backend.existence.StimulusMedium
import org.tendiwa.backend.existence.aspect
import org.tendiwa.backend.space.Reality
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.frontend.gdx2d.floor.FloorLayer
import org.tendiwa.frontend.gdx2d.gridActors.GridActorRegistry
import org.tendiwa.frontend.gdx2d.input.KeyCombinationPool
import org.tendiwa.frontend.gdx2d.input.TendiwaInputProcessor
import org.tendiwa.frontend.gdx2d.resources.images.NamedTextureCache
import org.tendiwa.frontend.generic.PlayerVolition
import org.tendiwa.frontend.generic.RenderingVicinity
import org.tendiwa.frontend.generic.hasWallAt
import org.tendiwa.math.integers.divCover
import org.tendiwa.plane.grid.dimensions.GridDimension
import org.tendiwa.plane.grid.masks.contains
import org.tendiwa.plane.grid.tiles.Tile

class TendiwaGame(
    private val obtainAtlas: () -> TextureAtlas,
    val reality: Reality,
    val playerVolition: PlayerVolition,
    private val stimulusMedium: StimulusMedium,
    private val plugins: List<TendiwaGdxClientPlugin>
) : ApplicationAdapter() {
    lateinit var textureCache: NamedTextureCache
    lateinit var stage: Stage
    lateinit var vicinity: RenderingVicinity
    lateinit var camera: TendiwaCamera
    lateinit var keyCombinationPool: KeyCombinationPool
    lateinit var frontendStimulusMedium: FrontendStimulusMedium
    lateinit var gridActorRegistry: GridActorRegistry
    lateinit var atlas: TextureAtlas
    lateinit var uiStage: Stage
    lateinit var inputProcessor: TendiwaInputProcessor

    override fun create() {
        atlas = obtainAtlas()
        initVicinity()
        initFacilities()
        initUi()
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
                vicinity.hasWallAt(x, y)
                    && vicinity.fieldOfView.contains(x, y)
                ) {
                    gridActorRegistry.spawnWall(Tile(x, y))
                }
            }
            vicinity.things.forEach {
                if (vicinity.fieldOfView.contains(it.aspect<Position>().tile)) {
                    gridActorRegistry.spawnRealThing(it)
                }
            }
        }
    }

    private fun initReactions() {
        frontendStimulusMedium = FrontendStimulusMedium()
        stimulusMedium.subscribeToAll(
            { frontendStimulusMedium.queueStimulus(it) }
        )
    }

    private fun initInput() {
        keyCombinationPool = KeyCombinationPool()
        inputProcessor = TendiwaInputProcessor(
            uiStage,
            keyCombinationPool,
            Gdx.input
        )
        Gdx.input.inputProcessor = inputProcessor
    }

    private fun initFacilities() {
        textureCache =
            NamedTextureCache(atlas)
        camera = TendiwaCamera()
        stage = Stage(
            FitViewport(
                Gdx.graphics.width.toFloat() / 32,
                Gdx.graphics.height.toFloat() / 32,
                camera
            ),
            SpriteBatch()
        )
        gridActorRegistry = GridActorRegistry(vicinity, textureCache, stage)
    }

    private fun initVicinity() {
        vicinity = RenderingVicinity(
            reality.space,
            GridDimension(
                Gdx.graphics.width divCover 32,
                Gdx.graphics.height divCover 32
            ),
            0
        )
    }

    private fun initPlugins() {
        plugins.forEach { it.init(this) }
    }

    private fun initUi() {
        uiStage = Stage()
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        frontendStimulusMedium.reactIfStimulated()
        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
        uiStage.act(Gdx.graphics.deltaTime)
        uiStage.draw()
    }

    override fun dispose() {
        stage.dispose()
    }
}

