package net.spartanb312.aquarius.config.setting.impl.other

import net.spartanb312.aquarius.util.common.key.KeyBind
import net.spartanb312.aquarius.manager.InputManager.register
import net.spartanb312.aquarius.config.setting.MutableSetting

class KeyBindSetting(
    name: String,
    value: KeyBind,
    visibility: (() -> Boolean) = { true },
    description: String = "",
) : MutableSetting<KeyBind>(name, value, description, visibility) {
    init {
        this.value.register()
    }
}