package net.spartanb312.aquarius.gui.interfaces

import net.spartanb312.aquarius.config.setting.AbstractSetting

interface IValueContent<T> {
    val setting: AbstractSetting<T>
}