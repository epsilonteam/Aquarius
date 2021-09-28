package net.aquarius.client.module

import net.aquarius.client.common.AbstractModule
import net.aquarius.client.common.Category
import org.lwjgl.input.Keyboard

open class Module(
    name: String,
    alias: Array<String> = emptyArray(),
    category: Category,
    description: String,
    priority: Int = 1000,
    visibleOnArray: Boolean = true,
    keyBind: Int = Keyboard.KEY_NONE
) : AbstractModule(name, alias, category, description, priority, keyBind, visibleOnArray)