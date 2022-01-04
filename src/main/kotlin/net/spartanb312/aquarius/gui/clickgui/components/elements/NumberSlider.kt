package net.spartanb312.aquarius.gui.clickgui.components.elements

import net.spartanb312.aquarius.gui.clickgui.components.AbstractElement
import net.spartanb312.aquarius.gui.clickgui.components.AnimatedAlphaUnit
import net.spartanb312.aquarius.gui.interfaces.IChildComponent
import net.spartanb312.aquarius.gui.interfaces.IFatherComponent
import net.spartanb312.aquarius.gui.interfaces.IValueContent
import net.spartanb312.aquarius.manager.GUIManager
import net.spartanb312.aquarius.config.setting.impl.number.NumberSetting
import net.spartanb312.aquarius.util.ColorUtils.endColor2
import net.spartanb312.aquarius.util.ColorUtils.startColor2
import net.spartanb312.aquarius.util.graphics.GUIHelper
import net.spartanb312.aquarius.util.graphics.RenderUtils2D
import net.spartanb312.aquarius.util.graphics.font.renderer.MainFontRenderer
import net.spartanb312.aquarius.util.math.Quad
import net.minecraft.util.math.MathHelper

class NumberSlider<T>(
    override var father: IFatherComponent,
    override val setting: NumberSetting<T>,
    override var x: Int = father.x,
    override var y: Int = father.y,
    override var height: Int = father.height,
    override var width: Int = father.width
) : AbstractElement(), IChildComponent, IValueContent<T>
        where T : Number, T : Comparable<T> {

    var sliding = false

    override val animatedAlphaUnit = AnimatedAlphaUnit()

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        //Background
        GUIHelper.renderBackground(this)
        if (!setting.isVisible) sliding = false

        if (this.sliding) {
            setting.setByPercent(MathHelper.clamp((mouseX - (x + 6f)) / (width - 9f), 0f, 1f))
        }

        //Animation
        GUIHelper.renderAnimation(this, mouseX, mouseY)
        //Line
        GUIHelper.drawLineLeft(this)
        RenderUtils2D.drawGradientRect(
            x + 6f,
            y + 1f,
            x + 6f + ((width - 9) * setting.getPercentBar()).toInt(),
            y + height - 1f,
            startColor2,
            startColor2,
            endColor2,
            endColor2,
        )

        MainFontRenderer.drawString(
            setting.name,
            x + 7f,
            y + height / 2 - MainFontRenderer.getHeight() / 2f,
            color = GUIManager.white
        )

        MainFontRenderer.drawString(
            setting.getDisplay(),
            x + width - 3 - MainFontRenderer.getWidth(setting.getDisplay()),
            y + height / 2 - MainFontRenderer.getHeight() / 2f,
            color = GUIManager.white
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
        if (button == 0) {
            sliding = true
            return true
        }
        return false
    }

    override fun onMouseReleased(x: Int, y: Int, state: Int) {
        sliding = false
    }

}