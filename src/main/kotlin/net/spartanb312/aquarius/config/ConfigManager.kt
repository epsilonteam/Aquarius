package net.spartanb312.aquarius.config

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.spartanb312.aquarius.util.common.extensions.runSafe
import net.spartanb312.aquarius.util.Logger

@Suppress("NOTHING_TO_INLINE")
object ConfigManager {

    val gsonPretty: Gson = GsonBuilder().setPrettyPrinting().create()
    val jsonParser = JsonParser()

    val moduleConfigs = mutableListOf<ModuleConfig>()
    val guiConfigs = mutableListOf<GuiConfig>()
    val otherConfigs = mutableListOf<Config>()

    inline fun register(config: Config) {
        when (config) {
            is ModuleConfig -> moduleConfigs.add(config)
            is GuiConfig -> guiConfigs.add(config)
            else -> otherConfigs.add(config)
        }
    }

    inline fun loadAll(parallel: Boolean = false) {
        Logger.info("Loading all Aquarius configs")
        loadModule(parallel = parallel)
        loadGUI(parallel = parallel)
        loadConfigs(parallel = parallel)
    }

    inline fun saveAll(parallel: Boolean = false) {
        Logger.info("Saving all Aquarius configs")
        saveModule(parallel = parallel)
        saveGUI(parallel = parallel)
        saveConfigs(parallel = parallel)
    }

    inline fun loadModule(parallel: Boolean) {
        loadConfigs(moduleConfigs, parallel)
    }

    inline fun saveModule(parallel: Boolean) {
        saveConfigs(moduleConfigs, parallel)
    }

    inline fun loadGUI(parallel: Boolean) {
        loadConfigs(guiConfigs, parallel)
    }

    inline fun saveGUI(parallel: Boolean) {
        saveConfigs(guiConfigs, parallel)
    }

    inline fun loadConfigs(configs: List<Config> = otherConfigs, parallel: Boolean = false) {
        runBlocking {
            configs.forEach {
                if (parallel) launch(Dispatchers.IO) {
                    if (!runSafe(false) { it.loadConfig() }) {
                        Logger.error("Can't load config : ${it.configName}")
                    }
                } else if (!runSafe(false)  { it.loadConfig() }) {
                    Logger.error("Can't load config : ${it.configName}")
                }
            }
        }
    }

    inline fun saveConfigs(configs: List<Config> = otherConfigs, parallel: Boolean = false) {
        runBlocking {
            configs.forEach {
                if (parallel) launch(Dispatchers.IO) {
                    if (!runSafe(false)  { it.saveConfig() }) {
                        Logger.error("Can't save config : ${it.configName}")
                    }
                } else if (!runSafe(false)  { it.saveConfig() }) {
                    Logger.error("Can't save config : ${it.configName}")
                }
            }
        }
    }
}