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
    private val queue: Queue<Stimulus> = ArrayDeque<Stimulus>(10)
    private var reactionInProgress = false
    private val reactionIsDone = { reactionInProgress = false }

    internal fun reactIfStimulated() {
        if (!reactionInProgress && !queue.isEmpty()) {
            reactionInProgress = true
            handleStimulus(queue.poll())
        }
    }

    internal fun queueStimulus(stimulus: Stimulus) {
        queue.add(stimulus)
    }

    private fun handleStimulus(stimulus: Stimulus) {
        stimuliToReactions[stimulus.javaClass]
            ?.forEach { it.react(stimulus, reactionIsDone) }
    }

    fun <S : Stimulus> registerReaction(
        stimuliClass: Class<S>,
        reaction: (stimulus: S, done: () -> Unit) -> Unit
    ) {
        stimuliToReactions
            .getOrPut(
                stimuliClass,
                { LinkedHashSet() }
            )
            .add(Reaction(reaction) as Reaction<Stimulus>)
    }

    private class Reaction<S : Stimulus>(
        val function: (stimulus: S, done: () -> Unit) -> Unit
    ) {
        fun react(stimulus: S, done: () -> Unit) {
            function(stimulus, done)
        }
    }
}

