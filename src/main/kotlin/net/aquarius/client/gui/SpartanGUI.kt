package net.aquarius.client.gui

import net.aquarius.client.config.ConfigManager
import net.aquarius.client.config.GuiConfig
import net.aquarius.client.management.Fonts
import net.aquarius.client.management.GUIManager
import net.aquarius.client.util.ColorRGB
import net.aquarius.client.util.ColorUtils
import net.aquarius.client.util.graphics.GlStateUtils
import net.aquarius.client.util.graphics.RenderUtils2D
import net.aquarius.client.util.graphics.VertexHelper
import net.aquarius.client.util.math.Quad
import net.aquarius.client.util.math.Vec2d
import net.minecraft.util.math.Vec2f
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.GL_LINE
import org.lwjgl.opengl.GL11.GL_LINES

class SpartanGUI(val name: String, val rootGUI: SpartanScreen, val hudEditor: SpartanScreen) {
    val config = GuiConfig(this).also {
        ConfigManager.register(it)
    }

    private val backgroundColor = ColorRGB(1, 1, 1, 128)

    var currentDescription: Quad<String, Int, Int, Int>? = null

    fun drawDescription() {
        if (currentDescription != null) {
            val width = Fonts.infoFont.getWidth(currentDescription!!.first) / 8f + 2
            val height = Fonts.infoFont.getHeight() / 8f + 2

            RenderUtils2D.drawRectFilled(
                currentDescription!!.second,
                currentDescription!!.third,
                (currentDescription!!.second + width).toInt(),
                (currentDescription!!.third + height).toInt(),
                backgroundColor,
            )

            val startColor =
                if (GUIManager.isRainbow) ColorUtils.rainbowRGB(currentDescription!!.fourth)
                else GUIManager.color.alpha(192)

            val endColor =
                if (GUIManager.isRainbow) ColorUtils.rainbowRGB(currentDescription!!.fourth + height.toInt() * 5)
                else GUIManager.color.alpha(192)

            val startX = currentDescription!!.second
            val startY = currentDescription!!.third
            val endX = startX + width.toInt()
            val endY = startY + height.toInt()

            RenderUtils2D.prepareGl()

            GL11.glLineWidth(1f)

            VertexHelper.begin(GL_LINES)
            VertexHelper.put(endX, startY, startColor)
            VertexHelper.put(startX, startY, startColor)

            VertexHelper.put(startX, startY, startColor)
            VertexHelper.put(startX, endY, endColor)

            VertexHelper.put(startX, endY, endColor)
            VertexHelper.put(endX, endY, endColor)

            VertexHelper.put(endX, endY, endColor)
            VertexHelper.put(endX, startY, startColor)
            VertexHelper.end()

            RenderUtils2D.releaseGl()

            Fonts.infoFont.drawString(
                currentDescription!!.first,
                currentDescription!!.second + 1f,
                currentDescription!!.third + 1f,
                GUIManager.white,
                scale = 0.125F
            )

            currentDescription = null
        }
    }

}