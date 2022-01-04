package net.spartanb312.aquarius.manager

import net.spartanb312.aquarius.config.ConfigManager
import net.spartanb312.aquarius.gui.AquariusGUI
import net.spartanb312.aquarius.gui.clickgui.HUDEditorScreen
import net.spartanb312.aquarius.gui.clickgui.RootScreen
import net.spartanb312.aquarius.module.setting.GuiSetting
import net.spartanb312.aquarius.util.ColorHSB
import net.spartanb312.aquarius.util.ColorRGB

object GUIManager {

    val defaultGUI = AquariusGUI(
        name = "DefaultGUI",
        rootGUI = RootScreen,
        hudEditor = HUDEditorScreen
    ).also { ConfigManager.register(it.config) }

    val white = ColorRGB(255, 255, 255, 255)

    val isParticle get() = GuiSetting.backgroundEffect == GuiSetting.BackgroundEffect.Particle
    val isRainbow get() = GuiSetting.rainbow

    val isBlur get() = GuiSetting.background == GuiSetting.Background.Blur || GuiSetting.background == GuiSetting.Background.Both
    val isShadow get() = GuiSetting.background == GuiSetting.Background.Shadow || GuiSetting.background == GuiSetting.Background.Both

    val saturation get() = GuiSetting.saturation
    val brightness get() = GuiSetting.brightness

    private val firstGUIColor get() = GuiSetting.guiColor

    private val rainbowColor: ColorRGB
        get() {
            val hue =
                floatArrayOf(System.currentTimeMillis() % (360 * 32) / (360f * 32) * GuiSetting.rainbowSpeed)
            return ColorHSB(hue[0], GuiSetting.saturation, GuiSetting.brightness).toRGB()
        }


    val color: ColorRGB
        get() {
            return if (isRainbow) rainbowColor
            else firstGUIColor
        }

}