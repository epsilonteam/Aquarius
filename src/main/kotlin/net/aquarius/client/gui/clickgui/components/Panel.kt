package net.aquarius.client.gui.clickgui.components

import net.aquarius.client.common.Category
import net.aquarius.client.gui.clickgui.HUDEditorScreen
import net.aquarius.client.gui.interfaces.IChildComponent
import net.aquarius.client.gui.interfaces.IFatherComponent
import net.aquarius.client.gui.clickgui.RootScreen
import net.aquarius.client.gui.clickgui.components.elements.ModuleButton
import net.aquarius.client.management.GUIManager
import net.aquarius.client.management.ModuleManager
import net.aquarius.client.module.setting.FontSetting
import net.aquarius.client.util.ColorUtils
import net.aquarius.client.util.Timer
import net.aquarius.client.util.graphics.GUIHelper
import net.aquarius.client.util.graphics.RenderUtils2D
import net.aquarius.client.util.graphics.font.renderer.MainFontRenderer
import java.util.*

class Panel(
    val category: Category,
    override var x: Int = 0,
    override var y: Int = 0,
    override var width: Int,
    override var height: Int,
    private val isHUD: Boolean = false
) : IFatherComponent {

    override var isActive: Boolean = true
    override var children: MutableList<IChildComponent> = mutableListOf()

    private var isDragging = false
    private var x2 = 0
    private var y2 = 0

    private val panelTimer = Timer()

    init {
        ModuleManager.modules.forEach {
            if (it.category == category) children.add(ModuleButton(it, this))
        }
    }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        if (isDragging) {
            x = x2 + mouseX
            y = y2 + mouseY
        }
        this.height = (MainFontRenderer.getHeight() + 7).toInt()
        this.width = (45 + 55 * FontSetting.sizeSetting.value).toInt()
        //Title background
        if (GUIManager.isRainbow) RenderUtils2D.drawGradientRect(
            x.toFloat(),
            y.toFloat(),
            x + width.toFloat(),
            y + height.toFloat(),
            ColorUtils.rainbowRGB(0),
            ColorUtils.rainbowRGB(0),
            ColorUtils.rainbowRGB(height * 5),
            ColorUtils.rainbowRGB(height * 5),
        ) else RenderUtils2D.drawRectFilled(
            x,
            y,
            x + width,
            y + height,
            GUIManager.color
        )
        //Title
        MainFontRenderer.drawString(
            category.showName,
            x + (width / 2f - MainFontRenderer.getWidth(category.showName) / 2f),
            y + height / 2f - MainFontRenderer.getHeight() / 2f,
            color = GUIManager.white
        )

        val space = (MainFontRenderer.getHeight() + 5).toInt()

        var startY = y + height
        if (children.isNotEmpty()) {
            var index = 0
            for (button in children) {
                index++
                if (isActive) {
                    if (!panelTimer.passed(index * 25)) continue
                } else {
                    if (panelTimer.passed((children.size - index) * 25)) continue
                }
                button.x = x
                button.y = startY
                button.onRender(mouseX, mouseY, partialTicks)
                startY += space

                val visibleSettings = (button as ModuleButton).children.filter { it.isVisible() }
                visibleSettings.forEachIndexed { i, component ->
                    var shouldRender = true
                    if (button.isActive) {
                        if (!button.buttonTimer.passed(i * 25)) shouldRender = false
                    } else {
                        if (button.buttonTimer.passed((visibleSettings.size - i) * 25)) shouldRender = false
                    }
                    if (shouldRender) {
                        component.x = button.x
                        component.y = startY
                        component.onRender(mouseX, mouseY, partialTicks)
                        startY += space
                    }
                }
            }
        }
        RenderUtils2D.drawRectFilled(
            x,
            startY,
            x + width,
            startY + 1,
            GUIHelper.backgroundColor
        )
    }

    override fun onMouseClicked(x: Int, y: Int, button: Int): Boolean {
        if (button == 0 && isHovered(x, y)) {
            x2 = this.x - x
            y2 = this.y - y
            isDragging = true
            if (isHUD)
                Collections.swap(HUDEditorScreen.panels, 0, HUDEditorScreen.panels.indexOf(this))
            else
                Collections.swap(RootScreen.panels, 0, RootScreen.panels.indexOf(this))
            return true
        }
        if (button == 1 && isHovered(x, y)) {
            isActive = !isActive
            panelTimer.reset()
            return true
        }
        return false
    }

    override fun onMouseReleased(x: Int, y: Int, state: Int) {
        if (state == 0) {
            this.isDragging = false
        }
        for (part in children) {
            part.onMouseReleased(x, y, state)
        }
    }

    override fun keyTyped(char: Char, key: Int): Boolean {
        for (part in children) {
            if (part.keyTyped(char, key)) return true
        }
        return false
    }

}