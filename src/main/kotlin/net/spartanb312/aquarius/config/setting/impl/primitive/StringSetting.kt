package net.spartanb312.aquarius.config.setting.impl.primitive

import net.spartanb312.aquarius.config.setting.MutableSetting

class StringSetting(
    name: String,
    value: String,
    visibility: (() -> Boolean) = { true },
    description: String = ""
) : MutableSetting<String>(name, value, description, visibility)