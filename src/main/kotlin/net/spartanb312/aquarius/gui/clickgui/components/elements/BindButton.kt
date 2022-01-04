package net.spartanb312.aquarius.gui.clickgui.components.elements

import net.spartanb312.aquarius.util.common.key.KeyBind
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
import org.lwjgl.input.Keyboard.*

class BindButton(
    override var father: IFatherComponent,
    override val setting: AbstractSetting<KeyBind>,
    override var x: Int = father.x,
    override var y: Int = father.y,
    override var height: Int = father.height,
    override var width: Int = father.width
) : AbstractElement(), IChildComponent, IValueContent<KeyBind> {

    var accepting = false

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
            "Bind | " + getKey().replace("CONTROL", "CTRL", true).replace("MENU", "ALT", true),
            x + 7f,
            y + height / 2 - MainFontRenderer.getHeight() / 2f,
            color = if (accepting) startColor2 else GUIManager.white
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

    fun getKey(): String {
        val list = mutableListOf<String>()
        setting.value.key.forEach {
            list.add(getKeyName(it))
        }
        return list.joinToString(separator = "+")
    }

    override fun keyTyped(char: Char, key: Int): Boolean {
        if (key == KEY_LCONTROL
            || key == KEY_RCONTROL
            || key == KEY_LMENU
            || key == KEY_RMENU
        ) return false
        if (accepting) {
            if (key == KEY_BACK) {
                setting.value.key = listOf(KEY_NONE).toIntArray()
            } else {
                val binds = mutableListOf(key).also {
                    if (isKeyDown(KEY_LCONTROL)) it.add(KEY_LCONTROL)
                    if (isKeyDown(KEY_RCONTROL)) it.add(KEY_RCONTROL)
                    if (isKeyDown(KEY_LMENU)) it.add(KEY_LMENU)
                    if (isKeyDown(KEY_RMENU)) it.add(KEY_RMENU)
                }
                setting.value.key = binds.toIntArray()
            }
            accepting = false
            return true
        }
        return false
    }

    override fun onMouseClicked(x: Int, y: Int, button: Int): Boolean {
        if (!isHovered(x, y)) return false
        if (button == 0) {
            accepting = !accepting
        }
        return true
    }

}