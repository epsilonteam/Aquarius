package net.spartanb312.aquarius.module.general

import net.minecraft.client.gui.GuiScreen
import net.spartanb312.aquarius.module.Category
import net.spartanb312.aquarius.config.ConfigManager
import net.spartanb312.aquarius.gui.clickgui.RootScreen
import net.spartanb312.aquarius.module.Module
import net.spartanb312.aquarius.module.setting.GuiSetting
import net.spartanb312.aquarius.util.Wrapper.mc
import org.lwjgl.input.Keyboard

internal object RootGUI : Module(
    name = "RootGUI",
    alias = arrayOf("Gui", "ClickGUI", "MainGUI"),
    category = Category.Client,
    description = "The root GUI of Aquarius",
    keyBind = Keyboard.KEY_RSHIFT
) {

    private var lastGuiScreen: GuiScreen? = null

    override fun onEnable() {
        val screen = RootScreen
        if (mc.currentScreen != screen) {
            RootScreen.openTime = if (GuiSetting.zoomIn) System.currentTimeMillis() else 0L
            mc.displayGuiScreen(screen)
            lastGuiScreen = screen
        }
    }

    override fun onDisable() {
        if (mc.currentScreen != null && mc.currentScreen == lastGuiScreen) {
            mc.displayGuiScreen(null)
        }
        lastGuiScreen = null
        ConfigManager.saveAll(true)
    }

}