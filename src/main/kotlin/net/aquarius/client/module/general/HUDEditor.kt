package net.aquarius.client.module.general

import net.minecraft.client.gui.GuiScreen
import net.aquarius.client.common.Category
import net.aquarius.client.config.ConfigManager
import net.aquarius.client.gui.clickgui.HUDEditorScreen
import net.aquarius.client.module.Module
import net.aquarius.client.util.Wrapper.mc
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