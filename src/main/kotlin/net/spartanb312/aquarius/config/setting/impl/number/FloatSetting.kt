package net.spartanb312.aquarius.config.setting.impl.number

class FloatSetting(
    name: String,
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    step: Float,
    visibility: (() -> Boolean) = { true },
    description: String = ""
) : NumberSetting<Float>(name, value, range, step, visibility, description, step) {

    override fun setByPercent(percent: Float) {
        value = range.start + ((range.endInclusive - range.start) * percent / step).toInt() * step
    }

    override fun getDisplay(): String {
        return String.format("%.1f", value)
    }

    override fun getPercentBar(): Float {
        return ((value - range.start) / (range.endInclusive - range.start))
    }

}