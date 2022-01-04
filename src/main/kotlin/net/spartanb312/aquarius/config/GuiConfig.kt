package net.spartanb312.aquarius.config

import net.spartanb312.aquarius.util.common.extensions.isNotExist
import net.spartanb312.aquarius.gui.AquariusGUI
import net.spartanb312.aquarius.Aquarius

class GuiConfig(
    val gui: AquariusGUI
) : Config("${gui.name}.json") {
    override val dirPath = Aquarius.DEFAULT_CONFIG_PATH + "config/gui/"

    override fun saveConfig() {
        if (configFile.isNotExist()) {
            configFile.parentFile.mkdirs()
            configFile.createNewFile()
        }
    }

    override fun loadConfig() {
        if (configFile.exists()) {
        } else saveConfig()
    }

}