package net.spartanb312.aquarius.config.setting

import net.spartanb312.aquarius.util.common.interfaces.Nameable
import kotlin.reflect.KProperty

abstract class AbstractSetting<T> : Nameable {

    abstract override val name: String
    abstract val defaultValue: T
    abstract val description: String
    abstract val visibility: (() -> Boolean)

    abstract var value: T
    val isVisible get() = visibility.invoke()
    val isModified get() = this.value != this.defaultValue

    val listeners = ArrayList<() -> Unit>()
    val valueListeners = ArrayList<(prev: T, input: T) -> Unit>()

    @Suppress("NOTHING_TO_INLINE")
    inline operator fun getValue(thisRef: Any?, property: KProperty<*>) = value

    fun reset() {
        value = defaultValue
    }

    infix fun listen(listener: () -> Unit) : AbstractSetting<T> {
        this.listeners.add(listener)
        return this
    }

    fun valueListen(listener: (prev: T, input: T) -> Unit) {
        this.valueListeners.add(listener)
    }

    override fun toString() = value.toString()

    override fun equals(other: Any?) = this === other
            || (other is AbstractSetting<*>
            && this.name == other.name
            && this.value == other.value)

    override fun hashCode() = name.hashCode() * 31 + value.hashCode()

}