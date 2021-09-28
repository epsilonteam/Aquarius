package net.aquarius.client.gui.clickgui.components.elements

import net.aquarius.client.gui.clickgui.components.AbstractElement
import net.aquarius.client.gui.clickgui.components.AnimatedAlphaUnit
import net.aquarius.client.gui.interfaces.IChildComponent
import net.aquarius.client.gui.interfaces.IFatherComponent
import net.aquarius.client.gui.interfaces.IValueContent
import net.aquarius.client.management.GUIManager
import net.aquarius.client.setting.AbstractSetting
import net.aquarius.client.util.ColorRGB
import net.aquarius.client.util.ColorUtils.startColor2
import net.aquarius.client.util.graphics.GUIHelper
import net.aquarius.client.util.graphics.RenderUtils2D
import net.aquarius.client.util.graphics.font.renderer.MainFontRenderer
import net.aquarius.client.util.math.Quad

class ColorPicker(
    override var father: IFatherComponent,
    override val setting: AbstractSetting<ColorRGB>,
    override var x: Int = father.x,
    override var y: Int = father.y,
    override var height: Int = father.height,
    override var width: Int = father.width
) : AbstractElement(), IChildComponent, IValueContent<ColorRGB> {

    override val animatedAlphaUnit = AnimatedAlphaUnit()

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        //Background
        GUIHelper.renderBackground(this)
        //Animation
        GUIHelper.renderAnimation(this, mouseX, mouseY)
        //Line
        GUIHelper.drawLineLeft(this)

        //Color
        RenderUtils2D.drawRectFilled(x + width - height - 3, y + 1, x + width - 3, y + height, setting.value)

        MainFontRenderer.drawString(
            setting.name,
            x + 7f,
            y + height / 2 - MainFontRenderer.getHeight() / 2f,
            color = startColor2
        )

        if (isHoovered(mouseX, mouseY) && getDescription() != "") {
            GUIManager.defaultGUI.currentDescription =
                Quad(getDescription(), mouseX + 10, mouseY, ((father as IChildComponent).father.y - mouseY) * 5)
        }
    }

    override fun getDescription(): String {
        return setting.description
    }

    override fun isVisible(): Boolean {
        return setting.isVisible
    }

    override fun onMouseClicked(x: Int, y: Int, button: Int): Boolean {
        if (!setting.isVisible || !isHovered(x, y)) return false
        return true
    }

}