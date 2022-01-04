package net.spartanb312.aquarius.gui.clickgui

import net.spartanb312.aquarius.module.Category
import net.spartanb312.aquarius.gui.HUDFrame
import net.spartanb312.aquarius.gui.AquariusScreen
import net.spartanb312.aquarius.gui.clickgui.components.Panel
import net.spartanb312.aquarius.gui.clickgui.components.ParticleRenderer
import net.spartanb312.aquarius.gui.clickgui.components.elements.ModuleButton
import net.spartanb312.aquarius.hud.HUDModule
import net.spartanb312.aquarius.manager.GUIManager
import net.spartanb312.aquarius.module.general.HUDEditor
import net.spartanb312.aquarius.util.Utils.getShaderGroup
import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import java.util.*

object HUDEditorScreen : AquariusScreen() {

    val panels = mutableListOf<Panel>()
    val hudList = mutableListOf<HUDFrame>()

    init {
        var startX = 5
        Category.values().forEach {
            if (it != Category.Hidden) {
                if (it.isHUD) {
                    panels.add(Panel(it, startX, 5, 100, 15, true))
                    startX += 105
                }
            }
        }
        panels.forEach { panel ->
            panel.children.forEach {
                hudList.add(((it as ModuleButton).module as HUDModule).hudFrame)
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
        if (GUIManager.isParticle) {
            ParticleRenderer.tick(10)
            ParticleRenderer.render()
        }
        mouseDrag()
        for (i in panels.indices.reversed()) {
            panels[i].onRender(mouseX, mouseY, partialTicks)
        }

        //HUDs
        hudList.reversed().forEach {
            it.onRender(mouseX, mouseY, partialTicks)
        }

        //Panels
        panels.reversed().forEach {
            it.onRender(mouseX, mouseY, partialTicks)
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
        for (hud in hudList) {
            if (hud.onMouseClicked(mouseX, mouseY, mouseButton)) {
                Collections.swap(hudList, 0, hudList.indexOf(hud))
                return
            }
        }
    }

    override fun onGuiClosed() {
        getShaderGroup()?.deleteShaderGroup()
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            HUDEditor.disable(notification = false)
            return
        }
        panels.forEach {
            if (it.keyTyped(typedChar, keyCode)) return
        }
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        panels.forEach {
            it.onMouseReleased(mouseX, mouseY, state)
        }
        hudList.forEach {
            it.onMouseReleased(mouseX, mouseY, state)
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