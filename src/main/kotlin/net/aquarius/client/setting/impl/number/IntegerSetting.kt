package net.aquarius.client.setting.impl.number

class IntegerSetting(
    name: String,
    value: Int,
    range: IntRange,
    step: Int,
    visibility: (() -> Boolean) = { true },
    description: String = ""
) : NumberSetting<Int>(name, value, range, step, visibility, description, step){

    override fun setByPercent(percent: Float) {
        value = range.start + ((range.endInclusive - range.start) * percent / step).toInt() * step
    }

    override fun getDisplay(): String {
        return value.toString()
    }

    override fun getPercentBar(): Float {
        return (value - range.start) / (range.endInclusive - range.start).toFloat()
    }

}