package net.aquarius.client.hud

import net.aquarius.client.common.AbstractModule
import net.aquarius.client.common.Category
import net.aquarius.client.gui.HUDFrame
import org.lwjgl.input.Keyboard

@Suppress("LeakingThis")
abstract class HUDModule(
    name: String,
    alias: Array<String> = emptyArray(),
    category: Category,
    description: String,
    x: Int = 0,
    y: Int = 0,
    height: Int = 0,
    width: Int = 0,
    priority: Int = 1000,
    visibleOnArray: Boolean = false,
    keyBind: Int = Keyboard.KEY_NONE
) : AbstractModule(
    name,
    alias,
    category,
    description,
    priority,
    keyBind,
    visibleOnArray
) {

    val hudFrame: HUDFrame = HUDFrame(this, x, y, width, height)

    abstract fun onRender()

    inline val x get() = hudFrame.x
    inline val y get() = hudFrame.y
    inline val width get() = hudFrame.width
    inline val height get() = hudFrame.height

    fun resize(block: HUDFrame.() -> Unit) {
        block.invoke(this.hudFrame)
    }

}