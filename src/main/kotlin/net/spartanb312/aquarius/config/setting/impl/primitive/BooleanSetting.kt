package net.spartanb312.aquarius.config.setting.impl.primitive

import net.spartanb312.aquarius.config.setting.MutableSetting

open class BooleanSetting(
    name: String,
    value: Boolean,
    visibility: (() -> Boolean) = { true },
    description: String = ""
) : MutableSetting<Boolean>(name, value, description, visibility)