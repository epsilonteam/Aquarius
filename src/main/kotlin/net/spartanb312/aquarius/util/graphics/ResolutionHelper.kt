package net.spartanb312.aquarius.util.graphics

import net.spartanb312.aquarius.util.Wrapper
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