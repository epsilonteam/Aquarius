package net.spartanb312.aquarius.config.setting.impl.number

class LongSetting(
    name: String,
    value: Long,
    range: LongRange,
    step: Long,
    visibility: (() -> Boolean) = { true },
    description: String = ""
) : NumberSetting<Long>(name, value, range, step, visibility, description, step) {

    override fun setByPercent(percent: Float) {
        value = range.start + ((range.endInclusive - range.start) * percent / step).toInt() * step
    }

    override fun getDisplay(): String {
        return value.toString()
    }

    override fun getPercentBar(): Float {
        return ((value - range.start) / (range.endInclusive - range.start).toDouble()).toFloat()
    }

}