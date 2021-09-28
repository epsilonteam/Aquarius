package net.aquarius.client.setting.impl.primitive

import net.aquarius.client.setting.MutableSetting

class StringSetting(
    name: String,
    value: String,
    visibility: (() -> Boolean) = { true },
    description: String = ""
) : MutableSetting<String>(name, value, description, visibility)