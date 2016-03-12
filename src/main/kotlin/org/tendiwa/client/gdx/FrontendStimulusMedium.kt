package org.tendiwa.client.gdx

import org.tendiwa.backend.existence.Stimulus
import java.util.*

/**
 * Proxies [stimuli][Stimulus] from [StimulusMedium] to plugins so those
 * stimuli can be handled asynchronously.
 */
class FrontendStimulusMedium {
    private val stimuliToReactions =
        LinkedHashMap<Class<out Stimulus>, MutableCollection<Reaction<in Stimulus>>>()

    internal fun handleStimulus(stimulus: Stimulus) {
        stimuliToReactions[stimulus.javaClass]
            ?.forEach { it.react(stimulus) }
    }

    fun <S : Stimulus> registerReaction(
        stimuliClass: Class<S>,
        reaction: (S) -> Unit
    ) {
        stimuliToReactions
            .getOrPut(
                stimuliClass,
                { LinkedHashSet() }
            )
            .add(
                Reaction(reaction) as Reaction<Stimulus>
            )
    }

    private class Reaction<S : Stimulus>(val function: (S) -> Unit) {
        fun react(stimulus: S) {
            function(stimulus)
        }
    }
}

