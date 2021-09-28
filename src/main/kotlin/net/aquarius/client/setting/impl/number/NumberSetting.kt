package net.aquarius.client.setting.impl.number

import net.aquarius.client.setting.MutableSetting

abstract class NumberSetting<T>(
    name: String,
    value: T,
    val range: ClosedRange<T>,
    val step: T,
    visibility: (() -> Boolean),
    description: String = "",
    val fineStep: T
) : MutableSetting<T>(name, value, description, visibility)
        where T : Number, T : Comparable<T> {

    abstract fun getDisplay(): String
    abstract fun getPercentBar(): Float
    abstract fun setByPercent(percent:Float)

    fun getMin(): Double {
        return range.start.toDouble()
    }

    fun getMax(): Double {
        return range.endInclusive.toDouble()
    }

}