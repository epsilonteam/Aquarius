package net.aquarius.client.setting

import kotlin.reflect.KProperty

open class MutableSetting<T : Any>(
    final override val name: String,
    valueIn: T,
    override val description: String = "",
    override val visibility: () -> Boolean,
) : AbstractSetting<T>() {


    override val defaultValue = valueIn
    override var value = valueIn
        set(value) {
            if (value != field) {
                val prev = field
                val new = value
                field = new
                valueListeners.forEach { it(prev, field) }
                listeners.forEach { it() }
            }
        }

    fun des(): MutableSetting<T> {
        return this
    }
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }

}