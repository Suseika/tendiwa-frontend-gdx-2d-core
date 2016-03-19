package org.tendiwa.client.gdx.gridActors

import com.badlogic.gdx.scenes.scene2d.Actor
import org.tendiwa.backend.existence.RealThing

/**
 * A plugin for [GridActorRegistry] that creates specific kinds of
 * [Actors][Actor]
 */
interface ActorFactory {
    /**
     * Checks if an actor for a [RealThing] should be created by this
     * [ActorFactory].
     * @return true if this [ActorFactory] shoul be used to spawn an actor
     * for the [RealThing], false otherwise.
     */
    fun creates(realThing: RealThing): Boolean

    fun create(realThing: RealThing): Actor
}
