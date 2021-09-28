package net.aquarius.client.gui

import net.aquarius.client.gui.interfaces.IComponent
import net.aquarius.client.hud.HUDModule
import net.aquarius.client.util.ColorRGB
import net.aquarius.client.util.graphics.RenderUtils2D

class HUDFrame(
    val hudModule: HUDModule,
    override var x: Int,
    override var y: Int,
    override var width: Int,
    override var height: Int
) : IComponent {

    private val dragging = Dragging(this)

    private val backgroundColor = ColorRGB(1, 1, 1, 128)

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        dragging.updatePos(mouseX, mouseY)
        RenderUtils2D.drawRectFilled(x, y, x + width, y + height, backgroundColor)
        hudModule.onRender()
    }

    override fun onMouseClicked(x: Int, y: Int, button: Int): Boolean {
        if (isHoovered(x, y)) {
            dragging.onClick(x, y, button)
            return true
        }
        return false
    }

    override fun onMouseReleased(x: Int, y: Int, state: Int) {
        dragging.release(state)
    }

}