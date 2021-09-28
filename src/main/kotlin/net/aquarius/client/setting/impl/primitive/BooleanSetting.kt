package net.aquarius.client.setting.impl.primitive

import net.aquarius.client.setting.MutableSetting

open class BooleanSetting(
    name: String,
    value: Boolean,
    visibility: (() -> Boolean) = { true },
    description: String = ""
) : MutableSetting<Boolean>(name, value, description, visibility)