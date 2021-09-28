package net.aquarius.client.module.setting

import net.aquarius.client.common.Category
import net.aquarius.client.module.Module
import net.aquarius.client.util.ColorRGB

object GuiSetting : Module(
    name = "GuiSetting",
    alias = arrayOf("Theme", "Custom", "Style"),
    category = Category.Setting,
    description = "Setting of GUI"
) {

    val guiColor by setting("GUIColor", ColorRGB(255, 0, 0, 255), "The first color of GUI")
    val rainbow by setting("Rainbow", false, "Rainbow color effect")
    val rainbowSpeed by setting("RainbowSpeed", 1.0F, 0.0F..10.0F, 0.1F, "The speed of rainbow color") { rainbow }
    val brightness by setting("Brightness", 1.0F, 0.0F..1.0F, 0.05F, "The brightness of rainbow color") { rainbow }
    val saturation by setting("Saturation", 0.8F, 0.0F..1.0F, 0.05F, "The saturation of rainbow color") { rainbow }
    val background by setting("Background", Background.Shadow, "The background of UI")
    val backgroundEffect by setting("Effect", BackgroundEffect.None, "The background effect of UI")

    enum class Background {
        Shadow,
        Blur,
        Both,
        None,
    }

    enum class BackgroundEffect {
        None,
        Particle
    }

}