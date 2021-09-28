package net.aquarius.client.gui.interfaces

import net.aquarius.client.setting.AbstractSetting

interface IValueContent<T> {
    val setting: AbstractSetting<T>
}