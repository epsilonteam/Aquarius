package net.aquarius.client.setting.impl.other

import net.aquarius.client.setting.AbstractSetting

class ListenerSetting(
    override val name: String,
    override val defaultValue: () -> Unit,
    override val description: String = "",
    override val visibility: () -> Boolean,
) : AbstractSetting<() -> Unit>() {
    override var value: () -> Unit = defaultValue
}