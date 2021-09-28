package net.aquarius.client.config

import net.aquarius.client.common.extensions.isNotExist
import net.aquarius.client.gui.SpartanGUI

class GuiConfig(
    val gui: SpartanGUI
) : Config("${gui.name}.json") {
    override val dirPath = net.aquarius.client.Aquarius.DEFAULT_CONFIG_PATH + "config/gui/"

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