package net.spartanb312.aquarius.gui.clickgui

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.util.ResourceLocation
import net.spartanb312.aquarius.module.Category
import net.spartanb312.aquarius.gui.AquariusScreen
import net.spartanb312.aquarius.gui.clickgui.components.Panel
import net.spartanb312.aquarius.gui.clickgui.components.ParticleRenderer
import net.spartanb312.aquarius.gui.clickgui.components.elements.ModuleButton
import net.spartanb312.aquarius.manager.GUIManager
import net.spartanb312.aquarius.module.general.RootGUI
import net.spartanb312.aquarius.util.Utils.getShaderGroup
import net.spartanb312.aquarius.util.graphics.Easing
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.GL11
import java.awt.Color

object RootScreen : AquariusScreen() {

    var openTime = 0L

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
        zoomAnimation()
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

    private fun zoomAnimation() {
        val scale = Easing.OUT_BACK.dec(Easing.toDelta(openTime, 300L), 1.0f, 1.25f)
        val resolution = ScaledResolution(mc)
        val centerX = (resolution.scaledWidth_double * 0.5f).toFloat()
        val centerY = (resolution.scaledHeight_double * 0.5f).toFloat()
        GL11.glTranslatef(centerX, centerY, 0.0f)
        GL11.glScalef(scale, scale, 1.0f)
        GL11.glTranslatef(-centerX, -centerY, 0.0f)
    }

}