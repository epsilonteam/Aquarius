package net.spartanb312.aquarius.module

import net.spartanb312.aquarius.util.common.interfaces.Alias
import net.spartanb312.aquarius.util.common.interfaces.Nameable
import net.spartanb312.aquarius.util.common.key.KeyBind
import net.spartanb312.aquarius.config.ConfigManager
import net.spartanb312.aquarius.config.ModuleConfig
import net.spartanb312.aquarius.manager.AquariusCore
import net.spartanb312.aquarius.notification.NotificationManager
import net.spartanb312.aquarius.config.setting.AbstractSetting
import net.spartanb312.aquarius.config.setting.SettingRegister
import net.spartanb312.aquarius.config.setting.impl.other.KeyBindSetting
import net.spartanb312.aquarius.config.setting.impl.primitive.EnumSetting

abstract class AbstractModule(
    final override val name: String,
    override val alias: Array<String> = emptyArray(),
    val category: Category,
    val description: String = "",
    val priority: Int,
    keyCode: Int,
    val visibility: Boolean,
) : Nameable, Alias, SettingRegister<AbstractModule> {

    val keyBind: KeyBind = KeyBind(keyCode, action = { toggle() })
    var isEnabled = false
    val isDisabled get() = !isEnabled
    private val keyListeners = mutableListOf<(Int) -> Unit>()

    @Suppress("LeakingThis")
    val config = ModuleConfig(this).also {
        ConfigManager.register(it)
    }

    enum class Visibility {
        ON,
        OFF
    }

    init {
        config.configs.add(
            EnumSetting(
                name = "Visibility",
                value = if (visibility) Visibility.ON else Visibility.OFF,
                description = "Determine whether the module should be displayed on array"
            )
        )
        config.configs.add(
            KeyBindSetting(
                name = "Bind",
                value = keyBind,
                description = "Bind a key to toggle this module"
            )
        )
    }

    fun saveConfig() {
        config.saveConfig()
        NotificationManager.debug("Saved config for module ${this.name}")
    }

    fun loadConfig() {
        config.loadConfig()
        NotificationManager.debug("Loaded config for module ${this.name}")
    }

    open fun onEnable() {
    }

    open fun onDisable() {
    }

    fun enable(notification: Boolean = true, silent: Boolean = false) {
        if (category == Category.Setting) {
            if (notification) NotificationManager.warn("You aren't allowed to enable a setting module!")
            return
        }
        if (notification) NotificationManager.moduleToggle(this, true)
        isEnabled = true
        AquariusCore.register(this)
        if (!silent) onEnable()
    }

    fun disable(notification: Boolean = true, silent: Boolean = false) {
        if (notification) NotificationManager.moduleToggle(this, false)
        isEnabled = false
        AquariusCore.unregister(this)
        if (!silent) onDisable()
    }

    fun reload() {
        onDisable()
        config.configs.forEach {
            it.reset()
        }
    }

    fun toggle() {
        if (isEnabled) disable()
        else enable()
    }

    override fun <S : AbstractSetting<*>> AbstractModule.setting(setting: S): S {
        this.config.configs.add(setting)
        return setting
    }

}