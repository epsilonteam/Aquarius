package net.aquarius.client.setting.impl.primitive

import net.aquarius.client.setting.MutableSetting
import net.aquarius.client.util.Utils.last
import net.aquarius.client.util.Utils.next

class EnumSetting<T : Enum<T>>(
    name: String,
    value: T,
    visibility: (() -> Boolean) = { true },
    description: String = ""
) : MutableSetting<T>(name, value, description, visibility) {

    private val enumClass: Class<T> = value.declaringClass
    private val enumValues: Array<out T> = enumClass.enumConstants

    fun nextValue() {
        value = value.next()
    }

    fun lastValue() {
        value = value.last()
    }

    fun currentName(): String {
        return value.name
    }

    fun setWithName(nameIn: String) {
        enumValues.firstOrNull { it.name.equals(nameIn, true) }?.let {
            value = it
        }
    }
}