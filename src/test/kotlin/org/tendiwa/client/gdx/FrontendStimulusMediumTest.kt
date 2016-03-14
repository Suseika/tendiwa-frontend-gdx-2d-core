package org.tendiwa.client.gdx

import org.junit.Test
import org.tendiwa.backend.existence.Stimulus
import kotlin.test.assertEquals

class FrontendStimulusMediumTest {
    @Test
    fun `forwards stimuli to registered reactions`() {
        val expectedPrice = 333
        var lastPriceChange = 0
        FrontendStimulusMedium().apply {
            registerReaction(
                stimuliClass = PriceChange::class.java,
                reaction = { stimulus, done ->
                    lastPriceChange = stimulus.delta
                    done()
                }
            )
            queueStimulus(PriceChange(expectedPrice))
            reactIfStimulated()
        }
        assertEquals(expectedPrice, lastPriceChange)
    }

    @Test
    fun `handles stimuli with 0 registered reactions`() {
        FrontendStimulusMedium().apply {
            queueStimulus(PriceChange(1))
            reactIfStimulated()
        }
    }

    @Test
    fun `can forward a stimulus to multiple reactions registered to it`() {
        val expectedPrice = 333
        var lastPriceChange = 0
        var priceChanged = false
        FrontendStimulusMedium().apply {
            registerReaction(
                stimuliClass = PriceChange::class.java,
                reaction = {
                    stimulus, done ->
                    lastPriceChange = stimulus.delta
                    done()
                }
            )
            registerReaction(
                stimuliClass = PriceChange::class.java,
                reaction = { stimulus, done ->
                    priceChanged = true
                    done()
                }
            )
            queueStimulus(PriceChange(expectedPrice))
            reactIfStimulated()
        }
        assertEquals(expectedPrice, lastPriceChange)
        assert(priceChanged)
    }

    class PriceChange(val delta: Int) : Stimulus
}
