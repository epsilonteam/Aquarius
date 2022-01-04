package net.spartanb312.aquarius.gui.clickgui.components.elements

import net.spartanb312.aquarius.gui.clickgui.components.AbstractElement
import net.spartanb312.aquarius.gui.clickgui.components.AnimatedAlphaUnit
import net.spartanb312.aquarius.gui.interfaces.IChildComponent
import net.spartanb312.aquarius.gui.interfaces.IFatherComponent
import net.spartanb312.aquarius.gui.interfaces.IValueContent
import net.spartanb312.aquarius.manager.GUIManager
import net.spartanb312.aquarius.config.setting.AbstractSetting
import net.spartanb312.aquarius.util.ColorUtils.startColor2
import net.spartanb312.aquarius.util.graphics.GUIHelper
import net.spartanb312.aquarius.util.graphics.font.renderer.MainFontRenderer
import net.spartanb312.aquarius.util.math.Quad

class BooleanButton(
    override var father: IFatherComponent,
    override val setting: AbstractSetting<Boolean>,
    override var x: Int = father.x,
    override var y: Int = father.y,
    override var height: Int = father.height,
    override var width: Int = father.width
) : AbstractElement(), IChildComponent, IValueContent<Boolean> {

    override val animatedAlphaUnit = AnimatedAlphaUnit()

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        //Background
        GUIHelper.renderBackground(this)
        //Animation
        GUIHelper.renderAnimation(this, mouseX, mouseY)
        //Line
        GUIHelper.drawLineLeft(this)

        MainFontRenderer.drawString(
            setting.name,
            x + 7f,
            y + height / 2 - MainFontRenderer.getHeight() / 2f,
            color = if (setting.value) startColor2 else GUIManager.white
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
            setting.value = !setting.value
        }
        return true
    }

}