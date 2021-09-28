package net.aquarius.client.util.graphics

import net.aquarius.client.gui.clickgui.components.AbstractElement
import net.aquarius.client.gui.interfaces.IChildComponent
import net.aquarius.client.gui.interfaces.IFatherComponent
import net.aquarius.client.management.GUIManager
import net.aquarius.client.util.ColorRGB
import net.aquarius.client.util.ColorUtils.endColor
import net.aquarius.client.util.ColorUtils.endColor2
import net.aquarius.client.util.ColorUtils.startColor
import net.aquarius.client.util.ColorUtils.startColor2

@Suppress("NOTHING_TO_INLINE")
object GUIHelper {

    val backgroundColor = ColorRGB(1, 1, 1, 128)

    inline fun renderAnimation(component: AbstractElement, mouseX: Int, mouseY: Int) {
        with(component) {
            if (isHovered(mouseX, mouseY)) {
                animatedAlphaUnit.reset()
            }
            animatedAlphaUnit.update()
            RenderUtils2D.drawRectFilled(
                x + 2,
                y + 1,
                x + width - 2,
                y + height - 1,
                ColorRGB(128, 128, 128, animatedAlphaUnit.alpha)
            )
        }
    }

    inline fun renderBackground(component: AbstractElement, isEnabled: Boolean = false) {
        with(component as IChildComponent) {
            //Background
            RenderUtils2D.drawRectFilled(x, y, x + width, y + height, backgroundColor)

            if (isEnabled) RenderUtils2D.drawGradientRect(
                x + 2f,
                y + 1f,
                x + width - 2f,
                y + height - 1f,
                startColor,
                startColor,
                endColor,
                endColor
            )
        }
    }

    inline fun drawLineLeft(component: AbstractElement) {
        with(component as IChildComponent) {
            RenderUtils2D.drawGradientRect(
                x + 5f,
                y.toFloat(),
                x + 6f,
                y + height.toFloat(),
                startColor2,
                startColor2,
                endColor2,
                endColor2
            )
        }

    }

}