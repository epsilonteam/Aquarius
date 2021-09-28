package net.aquarius.client.common

import net.aquarius.client.common.interfaces.Alias
import net.aquarius.client.common.interfaces.Nameable
import net.aquarius.client.common.key.KeyBind
import net.aquarius.client.config.ConfigManager
import net.aquarius.client.config.ModuleConfig
import net.aquarius.client.management.SpartanBus
import net.aquarius.client.notification.NotificationManager
import net.aquarius.client.setting.AbstractSetting
import net.aquarius.client.setting.SettingRegister
import net.aquarius.client.setting.impl.other.KeyBindSetting
import net.aquarius.client.setting.impl.primitive.EnumSetting

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
        SpartanBus.register(this)
        if (!silent) onEnable()
    }

    fun disable(notification: Boolean = true, silent: Boolean = false) {
        if (notification) NotificationManager.moduleToggle(this, false)
        isEnabled = false
        SpartanBus.unregister(this)
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