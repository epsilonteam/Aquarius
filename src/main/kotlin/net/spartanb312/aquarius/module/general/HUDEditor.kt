package net.spartanb312.aquarius.module.general

import net.minecraft.client.gui.GuiScreen
import net.spartanb312.aquarius.module.Category
import net.spartanb312.aquarius.config.ConfigManager
import net.spartanb312.aquarius.gui.clickgui.HUDEditorScreen
import net.spartanb312.aquarius.module.Module
import net.spartanb312.aquarius.util.Wrapper.mc
import org.lwjgl.input.Keyboard

internal object HUDEditor : Module(
    name = "HUD Editor",
    alias = arrayOf("HUD", "Render", "GUI"),
    category = Category.Client,
    description = "Edit your HUD",
    keyBind = Keyboard.KEY_GRAVE
) {

    private var lastGuiScreen: GuiScreen? = null

    override fun onEnable() {
        val screen = HUDEditorScreen
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