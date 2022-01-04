package net.spartanb312.aquarius

import net.spartanb312.aquarius.config.ConfigManager
import net.spartanb312.aquarius.event.EventBus.register
import net.spartanb312.aquarius.launch.Launch
import net.spartanb312.aquarius.manager.CommandManager
import net.spartanb312.aquarius.manager.Fonts
import net.spartanb312.aquarius.manager.GUIManager
import net.spartanb312.aquarius.manager.ModuleManager
import net.spartanb312.aquarius.module.general.HUDEditor
import net.spartanb312.aquarius.module.general.RootGUI
import net.spartanb312.aquarius.util.Logger
import net.spartanb312.aquarius.util.graphics.font.renderer.MainFontRenderer
import org.lwjgl.opengl.Display

@Launch("mixins.aquarius.json")
object Aquarius {

    const val MOD_NAME = "Aquarius"
    const val VERSION = "0.1"

    const val DEFAULT_COMMAND_PREFIX = "."
    const val DEFAULT_CONFIG_PATH = "Aquarius/"
    const val SCAN_GROUP = "net.spartanb312"

    @Launch.Tweak
    private fun onTweak() {
        Logger.info("Tweaking Aquarius")
    }

    @Launch.PreInit
    private fun preInit() {
        Logger.info("Pre initializing Aquarius")
    }

    @Launch.Init
    private fun onInit() {
        Logger.info("Initializing Aquarius")
        Display.setTitle("$MOD_NAME $VERSION")
        register(ModuleManager)
        register(CommandManager)
        register(GUIManager)
        MainFontRenderer
        Fonts
    }

    @Launch.PostInit
    private fun postInit() {
        Logger.info("Post initializing Aquarius")
        ConfigManager.loadAll(true)
        RootGUI.disable(notification = false, silent = true)
        HUDEditor.disable(notification = false, silent = true)
    }

    @Launch.Ready
    private fun onReady() {
        Logger.info("Aquarius is ready")
    }

}