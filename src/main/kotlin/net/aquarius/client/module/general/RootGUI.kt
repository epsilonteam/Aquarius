package net.aquarius.client.module.general

import net.minecraft.client.gui.GuiScreen
import net.aquarius.client.common.Category
import net.aquarius.client.config.ConfigManager
import net.aquarius.client.gui.clickgui.RootScreen
import net.aquarius.client.module.Module
import net.aquarius.client.util.Wrapper.mc
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