package org.tendiwa.client.gdx

import org.tendiwa.backend.space.Reality
import org.tendiwa.client.gdx.input.KeysSetup
import org.tendiwa.frontend.generic.PlayerVolition
import org.tendiwa.frontend.generic.RenderingVicinity

interface TendiwaGdxClientPlugin {
    fun init(
        camera: TendiwaCamera,
        vicinity: RenderingVicinity,
        playerVolition: PlayerVolition,
        keysSetup: KeysSetup,
        reality: Reality
    )
}
