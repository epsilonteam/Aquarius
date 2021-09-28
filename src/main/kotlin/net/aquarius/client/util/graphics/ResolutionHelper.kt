package net.aquarius.client.util.graphics

import net.aquarius.client.util.Wrapper
import net.minecraft.client.gui.ScaledResolution

object ResolutionHelper {

    var scaledResolution: ScaledResolution = ScaledResolution(Wrapper.mc); private set

    val height get() = scaledResolution.scaledHeight
    val width get() = scaledResolution.scaledWidth

    @JvmStatic
    fun reset() {
        scaledResolution = ScaledResolution(Wrapper.mc)
    }

}