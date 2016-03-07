package org.tendiwa.client.gdx

import org.tendiwa.client.gdx.input.KeysSetup
import org.tendiwa.frontend.generic.PlayerVolition
import org.tendiwa.frontend.generic.RenderingVicinity

interface TendiwaGdxClientPlugin {
    fun init(
        camera: TendiwaCamera,
        vicinity: RenderingVicinity,
        playerVolition: PlayerVolition,
        KeysSetup: KeysSetup
    )
}
