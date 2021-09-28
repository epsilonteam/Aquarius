package net.aquarius.client.gui.clickgui

import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation
import net.aquarius.client.common.Category
import net.aquarius.client.gui.SpartanScreen
import net.aquarius.client.gui.clickgui.components.Panel
import net.aquarius.client.gui.clickgui.components.ParticleRenderer
import net.aquarius.client.gui.clickgui.components.elements.ModuleButton
import net.aquarius.client.management.GUIManager
import net.aquarius.client.module.general.RootGUI
import net.aquarius.client.util.Utils.getShaderGroup
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import java.awt.Color

object RootScreen : SpartanScreen() {

    val panels = mutableListOf<Panel>()

    init {
        var startX = 5
        Category.values().forEach {
            if (it != Category.Hidden) {
                if (!it.isHUD) {
                    panels.add(Panel(it, startX, 5, 100, 15, false))
                    startX += 105
                }
            }
        }
    }

    override fun doesGuiPauseGame(): Boolean {
        return false
    }

    override fun initGui() {
        if (GUIManager.isBlur) {
            getShaderGroup()?.deleteShaderGroup()
            Minecraft.getMinecraft().entityRenderer.loadShader(ResourceLocation("shaders/post/blur.json"))
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        if (GUIManager.isShadow) {
            drawDefaultBackground()
        }
        if (mc.player == null) drawRect(0, 0, 9999, 9999, Color(0, 0, 0, 255).rgb)
        if (GUIManager.isParticle) {
            ParticleRenderer.tick(10)
            ParticleRenderer.render()
        }
        mouseDrag()
        for (i in panels.indices.reversed()) {
            panels[i].onRender(mouseX, mouseY, partialTicks)
        }
        GUIManager.defaultGUI.drawDescription()
    }


    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        for (panel in panels) {
            if (panel.onMouseClicked(mouseX, mouseY, mouseButton)) return
            if (!panel.isActive) continue
            for (part in panel.children) {
                if (part.onMouseClicked(mouseX, mouseY, mouseButton)) return
                if (!(part as ModuleButton).isActive) continue
                for (component in part.children) {
                    if (!component.isVisible()) continue
                    if (component.onMouseClicked(mouseX, mouseY, mouseButton)) return
                }
            }
        }
    }

    override fun onGuiClosed() {
        getShaderGroup()?.deleteShaderGroup()
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            RootGUI.disable()
            return
        }
        for (panel in panels) {
            if (panel.keyTyped(typedChar, keyCode)) return
        }
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        for (panel in panels) {
            panel.onMouseReleased(mouseX, mouseY, state)
        }
    }

    private fun mouseDrag() {
        val dWheel = Mouse.getDWheel()
        if (dWheel < 0) {
            panels.forEach { component: Panel -> component.y -= 10 }
        } else if (dWheel > 0) {
            panels.forEach { component: Panel -> component.y += 10 }
        }
    }

}