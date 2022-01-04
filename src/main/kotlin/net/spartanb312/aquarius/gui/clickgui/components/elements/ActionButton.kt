package net.spartanb312.aquarius.gui.clickgui.components.elements

import net.spartanb312.aquarius.gui.interfaces.IChildComponent
import net.spartanb312.aquarius.gui.interfaces.IFatherComponent
import net.spartanb312.aquarius.gui.interfaces.IValueContent
import net.spartanb312.aquarius.gui.clickgui.components.AbstractElement
import net.spartanb312.aquarius.gui.clickgui.components.AnimatedAlphaUnit
import net.spartanb312.aquarius.manager.GUIManager
import net.spartanb312.aquarius.config.setting.AbstractSetting
import net.spartanb312.aquarius.util.graphics.GUIHelper
import net.spartanb312.aquarius.util.graphics.font.renderer.MainFontRenderer
import net.spartanb312.aquarius.util.math.Quad

class ActionButton(
    override var father: IFatherComponent,
    override val setting: AbstractSetting<() -> Unit>,
    override var x: Int = father.x,
    override var y: Int = father.y,
    override var height: Int = father.height,
    override var width: Int = father.width
) : AbstractElement(), IChildComponent, IValueContent<() -> Unit> {

    override val animatedAlphaUnit = AnimatedAlphaUnit()

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        //Background
        GUIHelper.renderBackground(this)
        //Animation
        GUIHelper.renderAnimation(this, mouseX, mouseY)
        //Line
        GUIHelper.drawLineLeft(this)
        //String
        MainFontRenderer.drawString(
            setting.name,
            x + 7f,
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
        setting.value.invoke()
        return true
    }

}