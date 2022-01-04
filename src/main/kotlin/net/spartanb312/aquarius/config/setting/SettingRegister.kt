package net.spartanb312.aquarius.config.setting

import net.spartanb312.aquarius.util.common.interfaces.Nameable
import net.spartanb312.aquarius.util.common.key.KeyBind
import net.spartanb312.aquarius.config.setting.impl.number.DoubleSetting
import net.spartanb312.aquarius.config.setting.impl.number.FloatSetting
import net.spartanb312.aquarius.config.setting.impl.number.IntegerSetting
import net.spartanb312.aquarius.config.setting.impl.number.LongSetting
import net.spartanb312.aquarius.config.setting.impl.other.ColorSetting
import net.spartanb312.aquarius.config.setting.impl.other.KeyBindSetting
import net.spartanb312.aquarius.config.setting.impl.primitive.BooleanSetting
import net.spartanb312.aquarius.config.setting.impl.primitive.EnumSetting
import net.spartanb312.aquarius.config.setting.impl.primitive.StringSetting
import net.spartanb312.aquarius.util.ColorRGB

interface SettingRegister<T : Nameable> {

    fun T.setting(
        name: String,
        value: Double,
        range: ClosedFloatingPointRange<Double>,
        step: Double,
        description: String = "",
        visibility: (() -> Boolean) = { true },
    ) = setting(DoubleSetting(name, value, range, step, visibility, description))

    fun T.setting(
        name: String,
        value: Float,
        range: ClosedFloatingPointRange<Float>,
        step: Float,
        description: String = "",
        visibility: (() -> Boolean) = { true },
    ) = setting(FloatSetting(name, value, range, step, visibility, description))

    fun T.setting(
        name: String,
        value: Int,
        range: IntRange,
        step: Int,
        description: String = "",
        visibility: (() -> Boolean) = { true },
    ) = setting(IntegerSetting(name, value, range, step, visibility, description))

    fun T.setting(
        name: String,
        value: Long,
        range: LongRange,
        step: Long,
        description: String = "",
        visibility: (() -> Boolean) = { true },
    ) = setting(LongSetting(name, value, range, step, visibility, description))

    fun T.setting(
        name: String,
        value: ColorRGB,
        description: String = "",
        visibility: (() -> Boolean) = { true },
    ) = setting(ColorSetting(name, value, visibility, description))

    fun T.setting(
        name: String,
        value: Boolean,
        description: String = "",
        visibility: (() -> Boolean) = { true },
    ) = setting(BooleanSetting(name, value, visibility, description))

    fun T.setting(
        name: String,
        value: KeyBind,
        description: String = "",
        visibility: (() -> Boolean) = { true },
    ) = setting(KeyBindSetting(name, value, visibility, description))

    fun <E : Enum<E>> T.setting(
        name: String,
        value: E,
        description: String = "",
        visibility: (() -> Boolean) = { true },
    ) = setting(EnumSetting(name, value, visibility, description))

    fun T.setting(
        name: String,
        value: String,
        description: String = "",
        visibility: (() -> Boolean) = { true },
    ) = setting(StringSetting(name, value, visibility, description))

    fun <S : AbstractSetting<*>> T.setting(setting: S): S

}