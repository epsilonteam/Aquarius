package net.aquarius.client.gui.clickgui.components.elements

import net.aquarius.client.common.AbstractModule
import net.aquarius.client.gui.interfaces.IChildComponent
import net.aquarius.client.gui.interfaces.IFatherComponent
import net.aquarius.client.gui.clickgui.components.AbstractElement
import net.aquarius.client.gui.clickgui.components.AnimatedAlphaUnit
import net.aquarius.client.management.GUIManager
import net.aquarius.client.setting.impl.number.NumberSetting
import net.aquarius.client.setting.impl.other.ColorSetting
import net.aquarius.client.setting.impl.other.KeyBindSetting
import net.aquarius.client.setting.impl.other.ListenerSetting
import net.aquarius.client.setting.impl.primitive.BooleanSetting
import net.aquarius.client.setting.impl.primitive.EnumSetting
import net.aquarius.client.setting.impl.primitive.StringSetting
import net.aquarius.client.util.Timer
import net.aquarius.client.util.graphics.GUIHelper
import net.aquarius.client.util.graphics.font.renderer.MainFontRenderer
import net.aquarius.client.util.math.Quad

class ModuleButton(
    val module: AbstractModule,
    override var father: IFatherComponent,
    override var x: Int = father.x,
    override var y: Int = father.y,
    override var height: Int = father.height,
    override var width: Int = father.width
) : AbstractElement(), IFatherComponent, IChildComponent {

    override var isActive: Boolean = false
    override var children: MutableList<IChildComponent> = mutableListOf()
    val buttonTimer = Timer()

    override val animatedAlphaUnit = AnimatedAlphaUnit()

    init {
        module.config.configs.forEach {
            when (it) {
                is KeyBindSetting -> children.add(BindButton(father = this, setting = it))
                is ListenerSetting -> children.add(ActionButton(father = this, setting = it))
                is BooleanSetting -> children.add(BooleanButton(father = this, setting = it))
                is ColorSetting -> children.add(ColorPicker(father = this, setting = it))
                is EnumSetting -> children.add(EnumButton(father = this, setting = it))
                is NumberSetting -> children.add(NumberSlider(father = this, setting = it))
                is StringSetting -> children.add(StringField(father = this, setting = it))
            }
        }
    }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        this.height = (MainFontRenderer.getHeight() + 5).toInt()
        this.width = father.width
        this.children.forEach {
            it.height = this.height
            it.width = this.width
        }
        //Background
        GUIHelper.renderBackground(this, module.isEnabled)
        //Animation
        GUIHelper.renderAnimation(this, mouseX, mouseY)
        MainFontRenderer.drawString(
            module.name,
            x + 3f,
            y + height / 2 - MainFontRenderer.getHeight() / 2f,
            color = GUIManager.white
        )

        if (isHoovered(mouseX, mouseY) && getDescription() != "") {
            GUIManager.defaultGUI.currentDescription =
                Quad(getDescription(), mouseX + 10, mouseY, (father.y - mouseY) * 5)
        }
    }

    override fun getDescription(): String {
        return module.description
    }

    override fun onMouseClicked(x: Int, y: Int, button: Int): Boolean {
        if (!isHovered(x, y)) return false
        when (button) {
            0 -> module.toggle()
            1 -> {
                isActive = !isActive
                buttonTimer.reset()
            }
        }
        return true
    }

    override fun onMouseReleased(x: Int, y: Int, state: Int) {
        for (setting in children) {
            setting.onMouseReleased(x, y, state)
        }
    }

    override fun keyTyped(char: Char, key: Int): Boolean {
        for (setting in children) {
            if (setting.keyTyped(char, key)) return true
        }
        return false
    }

}