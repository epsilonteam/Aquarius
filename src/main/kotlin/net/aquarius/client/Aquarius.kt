package net.aquarius.client

import net.aquarius.client.Aquarius.MOD_ID
import net.aquarius.client.Aquarius.MOD_NAME
import net.aquarius.client.Aquarius.VERSION
import net.aquarius.client.config.ConfigManager
import net.aquarius.client.launch.api.Loadable
import net.aquarius.client.launch.api.SpartanMod
import net.aquarius.client.management.CommandManager
import net.aquarius.client.management.Fonts
import net.aquarius.client.management.GUIManager
import net.aquarius.client.management.ModuleManager
import net.aquarius.client.module.general.HUDEditor
import net.aquarius.client.module.general.RootGUI
import net.aquarius.client.util.Logger
import net.aquarius.client.util.graphics.font.renderer.MainFontRenderer
import org.lwjgl.opengl.Display

@SpartanMod(
    name = MOD_NAME,
    id = MOD_ID,
    version = VERSION,
    description = "A Utility mod for Minecraft based on SpartanBase",
    mixinFile = "mixins.aquarius.json"
)
object Aquarius : Loadable {

    const val MOD_NAME = "Aquarius"
    const val MOD_ID = "aquarius"
    const val VERSION = "0.1"

    const val DEFAULT_COMMAND_PREFIX = "."
    const val DEFAULT_CONFIG_PATH = "Aquarius/"

    override fun preInit() {
        Logger.info("Pre initializing Aquarius")
        Display.setTitle("$MOD_NAME $VERSION")
        register(ModuleManager)
        register(CommandManager)
        register(GUIManager)
        MainFontRenderer
        Fonts
    }

    override fun postInit() {
        Logger.info("Post initializing Aquarius")
        ConfigManager.loadAll(true)
        RootGUI.disable(notification = false, silent = true)
        HUDEditor.disable(notification = false, silent = true)
    }

}