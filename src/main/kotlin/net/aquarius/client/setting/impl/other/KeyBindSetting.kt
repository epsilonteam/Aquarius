package net.aquarius.client.setting.impl.other

import net.aquarius.client.common.key.KeyBind
import net.aquarius.client.management.InputManager.register
import net.aquarius.client.setting.MutableSetting

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