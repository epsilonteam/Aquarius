package net.aquarius.client.management

import net.aquarius.client.config.ConfigManager
import net.aquarius.client.gui.SpartanGUI
import net.aquarius.client.gui.clickgui.HUDEditorScreen
import net.aquarius.client.gui.clickgui.RootScreen
import net.aquarius.client.module.setting.GuiSetting
import net.aquarius.client.util.ColorHSB
import net.aquarius.client.util.ColorRGB
import java.awt.Color

object GUIManager {

    val defaultGUI = SpartanGUI(
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