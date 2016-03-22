package org.tendiwa.frontend.gdx2d

import org.tendiwa.backend.existence.Stimulus
import org.tendiwa.frontend.gdx2d.TendiwaGame

abstract class GameReaction<S : Stimulus>(
    protected val game: TendiwaGame
) : (S, () -> Unit) -> Unit
