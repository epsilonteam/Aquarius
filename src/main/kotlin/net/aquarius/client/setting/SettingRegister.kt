package net.aquarius.client.setting

import net.aquarius.client.common.interfaces.Nameable
import net.aquarius.client.common.key.KeyBind
import net.aquarius.client.setting.impl.number.DoubleSetting
import net.aquarius.client.setting.impl.number.FloatSetting
import net.aquarius.client.setting.impl.number.IntegerSetting
import net.aquarius.client.setting.impl.number.LongSetting
import net.aquarius.client.setting.impl.other.ColorSetting
import net.aquarius.client.setting.impl.other.KeyBindSetting
import net.aquarius.client.setting.impl.primitive.BooleanSetting
import net.aquarius.client.setting.impl.primitive.EnumSetting
import net.aquarius.client.setting.impl.primitive.StringSetting
import net.aquarius.client.util.ColorRGB

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