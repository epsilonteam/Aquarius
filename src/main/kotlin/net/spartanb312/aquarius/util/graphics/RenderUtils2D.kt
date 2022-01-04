package net.spartanb312.aquarius.util.graphics

import net.spartanb312.aquarius.util.ColorRGB
import net.spartanb312.aquarius.util.Wrapper
import net.spartanb312.aquarius.util.math.MathUtils
import net.spartanb312.aquarius.util.math.Vec2d
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.item.ItemStack
import org.lwjgl.opengl.GL11.*
import kotlin.math.*

@Suppress("NOTHING_TO_INLINE")
object RenderUtils2D {
    val mc = Wrapper.mc

    inline fun drawItem(itemStack: ItemStack, x: Int, y: Int, text: String? = null, drawOverlay: Boolean = true) {
        GlStateUtils.blend(true)
        GlStateUtils.depth(true)
        RenderHelper.enableGUIStandardItemLighting()

        mc.renderItem.zLevel = 0.0f
        mc.renderItem.renderItemAndEffectIntoGUI(itemStack, x, y)
        if (drawOverlay) mc.renderItem.renderItemOverlayIntoGUI(mc.fontRenderer, itemStack, x, y, text)
        mc.renderItem.zLevel = 0.0f

        RenderHelper.disableStandardItemLighting()
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)

        GlStateUtils.depth(false)
        GlStateUtils.texture2d(true)
    }

    inline fun drawRoundedRectOutline(
        posBegin: Vec2d = Vec2d(0.0, 0.0),
        posEnd: Vec2d,
        radius: Double,
        segments: Int = 0,
        lineWidth: Float,
        color: ColorRGB
    ) {
        val pos2 = Vec2d(posEnd.x, posBegin.y) // Top right
        val pos4 = Vec2d(posBegin.x, posEnd.y) // Bottom left

        drawArcOutline(posBegin.plus(radius), radius, Pair(-90f, 0f), segments, lineWidth, color) // Top left
        drawArcOutline(pos2.plus(-radius, radius), radius, Pair(0f, 90f), segments, lineWidth, color) // Top right
        drawArcOutline(posEnd.minus(radius), radius, Pair(90f, 180f), segments, lineWidth, color) // Bottom right
        drawArcOutline(pos4.plus(radius, -radius), radius, Pair(180f, 270f), segments, lineWidth, color) // Bottom left

        drawLine(posBegin.plus(radius, 0.0), pos2.minus(radius, 0.0), lineWidth, color) // Top
        drawLine(posBegin.plus(0.0, radius), pos4.minus(0.0, radius), lineWidth, color) // Left
        drawLine(pos2.plus(0.0, radius), posEnd.minus(0.0, radius), lineWidth, color) // Right
        drawLine(pos4.plus(radius, 0.0), posEnd.minus(radius, 0.0), lineWidth, color) // Bottom
    }

    inline fun drawRoundedRectFilled(
        posBegin: Vec2d = Vec2d(0.0, 0.0),
        posEnd: Vec2d,
        radius: Double,
        segments: Int = 0,
        color: ColorRGB
    ) {
        val pos2 = Vec2d(posEnd.x, posBegin.y) // Top right
        val pos4 = Vec2d(posBegin.x, posEnd.y) // Bottom left

        drawArcFilled(posBegin.plus(radius), radius, Pair(-90f, 0f), segments, color) // Top left
        drawArcFilled(pos2.plus(-radius, radius), radius, Pair(0f, 90f), segments, color) // Top right
        drawArcFilled(posEnd.minus(radius), radius, Pair(90f, 180f), segments, color) // Bottom right
        drawArcFilled(pos4.plus(radius, -radius), radius, Pair(180f, 270f), segments, color) // Bottom left

        drawRectFilled(posBegin.plus(radius, 0.0), pos2.plus(-radius, radius), color) // Top
        drawRectFilled(posBegin.plus(0.0, radius), posEnd.minus(0.0, radius), color) // Center
        drawRectFilled(pos4.plus(radius, -radius), posEnd.minus(radius, 0.0), color) // Bottom
    }

    inline fun drawCircleOutline(
        center: Vec2d = Vec2d(0.0, 0.0),
        radius: Double,
        segments: Int = 0,
        lineWidth: Float = 1f,
        color: ColorRGB
    ) {
        drawArcOutline(center, radius, Pair(0f, 360f), segments, lineWidth, color)
    }

    inline fun drawCircleFilled(center: Vec2d = Vec2d(0.0, 0.0), radius: Double, segments: Int = 0, color: ColorRGB) {
        drawArcFilled(center, radius, Pair(0f, 360f), segments, color)
    }

    inline fun drawArcOutline(
        center: Vec2d = Vec2d(0.0, 0.0),
        radius: Double,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        lineWidth: Float = 1f,
        color: ColorRGB
    ) {
        val arcVertices = getArcVertices(center, radius, angleRange, segments)
        drawLineStrip(arcVertices, lineWidth, color)
    }

    inline fun drawArcFilled(
        center: Vec2d = Vec2d(0.0, 0.0),
        radius: Double,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        color: ColorRGB
    ) {
        val arcVertices = getArcVertices(center, radius, angleRange, segments)
        drawTriangleFan(center, arcVertices, color)
    }

    inline fun drawRectOutline(
        posBegin: Vec2d = Vec2d(0.0, 0.0),
        posEnd: Vec2d,
        lineWidth: Float = 1f,
        color: ColorRGB
    ) {
        val pos2 = Vec2d(posEnd.x, posBegin.y) // Top right
        val pos4 = Vec2d(posBegin.x, posEnd.y) // Bottom left
        val vertices = arrayOf(posBegin, pos2, posEnd, pos4)
        drawLineLoop(vertices, lineWidth, color)
    }

    inline fun drawRectFilled(x: Int, y: Int, endX: Int, endY: Int, color: ColorRGB) {
        val pos1 = Vec2d(x.toDouble(), y.toDouble())
        val pos2 = Vec2d(endX.toDouble(), y.toDouble()) // Top right
        val pos3 = Vec2d(endX.toDouble(), endY.toDouble())
        val pos4 = Vec2d(x.toDouble(), endY.toDouble()) // Bottom left
        drawQuad(pos1, pos2, pos3, pos4, color)
    }

    inline fun drawRectFilled(posBegin: Vec2d = Vec2d(0.0, 0.0), posEnd: Vec2d, color: ColorRGB) {
        val pos2 = Vec2d(posEnd.x, posBegin.y) // Top right
        val pos4 = Vec2d(posBegin.x, posEnd.y) // Bottom left
        drawQuad(posBegin, pos2, posEnd, pos4, color)
    }

    inline fun drawQuad(pos1: Vec2d, pos2: Vec2d, pos3: Vec2d, pos4: Vec2d, color: ColorRGB) {
        val vertices = arrayOf(pos1, pos2, pos4, pos3)
        drawTriangleStrip(vertices, color)
    }

    inline fun drawQuad(pos1: Vec2d, pos2: Vec2d, pos3: Vec2d, pos4: Vec2d, color: Int) {
        val vertices = arrayOf(pos1, pos2, pos4, pos3)
        drawTriangleStrip(vertices, color)
    }

    inline fun drawTriangleOutline(pos1: Vec2d, pos2: Vec2d, pos3: Vec2d, lineWidth: Float = 1f, color: ColorRGB) {
        val vertices = arrayOf(pos1, pos2, pos3)
        drawLineLoop(vertices, lineWidth, color)
    }

    inline fun drawTriangleFilled(pos1: Vec2d, pos2: Vec2d, pos3: Vec2d, color: ColorRGB) {
        prepareGl()

        VertexHelper.begin(GL_TRIANGLES)
        VertexHelper.put(pos1, color)
        VertexHelper.put(pos2, color)
        VertexHelper.put(pos3, color)
        VertexHelper.end()

        releaseGl()
    }

    inline fun drawTriangleFan(center: Vec2d, vertices: Array<Vec2d>, color: ColorRGB) {
        prepareGl()

        VertexHelper.begin(GL_TRIANGLE_FAN)
        VertexHelper.put(center, color)
        for (vertex in vertices) {
            VertexHelper.put(vertex, color)
        }
        VertexHelper.end()

        releaseGl()
    }

    inline fun drawGradientRect(
        x: Float,
        y: Float,
        endX: Float,
        endY: Float,
        color1: ColorRGB,
        color2: ColorRGB,
        color3: ColorRGB,
        color4: ColorRGB
    ) {
        GlStateUtils.texture2d(false)
        GlStateUtils.blend(true)
        GlStateUtils.alpha(false)
        GlStateManager.tryBlendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
        )
        GlStateUtils.smooth(true)

        VertexHelper.begin(GL_QUADS)

        VertexHelper.put(endX.toDouble(), y.toDouble(), color1) //右上
        VertexHelper.put(x.toDouble(), y.toDouble(), color2) //左上
        VertexHelper.put(x.toDouble(), endY.toDouble(), color3) //左下
        VertexHelper.put(endX.toDouble(), endY.toDouble(), color4) //右下

        VertexHelper.end()

        GlStateUtils.blend(false)
        GlStateUtils.alpha(true)
        GlStateUtils.texture2d(true)
    }

    inline fun drawHorizontalRect(
        x: Int,
        y: Int,
        endX: Int,
        endY: Int,
        color1: ColorRGB,
        color2: ColorRGB
    ) {
        prepareGl()

        VertexHelper.begin(GL_QUADS)
        VertexHelper.put(x.toDouble(), y.toDouble(), color1) //左上
        VertexHelper.put(endX.toDouble(), y.toDouble(), color2) //右上
        VertexHelper.put(endX.toDouble(), endY.toDouble(), color2) //右下
        VertexHelper.put(x.toDouble(), endY.toDouble(), color1) //左下
        VertexHelper.end()

        releaseGl()
    }

    inline fun drawRect(
        x: Int,
        y: Int,
        endX: Int,
        endY: Int,
        color1: ColorRGB,
        color2: ColorRGB,
        color3: ColorRGB,
        color4: ColorRGB
    ) {
        prepareGl()

        VertexHelper.begin(GL_TRIANGLE_STRIP)
        VertexHelper.put(x.toDouble(), y.toDouble(), color1) //左上
        VertexHelper.put(endX.toDouble(), y.toDouble(), color2) //右上
        VertexHelper.put(endX.toDouble(), endY.toDouble(), color3) //右下
        VertexHelper.put(x.toDouble(), endY.toDouble(), color4) //左下
        VertexHelper.end()

        releaseGl()
    }

    inline fun drawTriangleStrip(vertices: Array<Vec2d>, color: ColorRGB) {
        prepareGl()

        VertexHelper.begin(GL_TRIANGLE_STRIP)
        for (vertex in vertices) {
            VertexHelper.put(vertex, color)
        }
        VertexHelper.end()

        releaseGl()
    }

    inline fun drawTriangleStrip(vertices: Array<Vec2d>, color: Int) {
        prepareGl()

        VertexHelper.begin(GL_TRIANGLE_STRIP)
        for (vertex in vertices) {
            VertexHelper.put(vertex, color)
        }
        VertexHelper.end()

        releaseGl()
    }

    inline fun drawLineLoop(vertices: Array<Vec2d>, lineWidth: Float = 1f, color: ColorRGB) {
        prepareGl()
        glLineWidth(lineWidth)

        VertexHelper.begin(GL_LINE_LOOP)
        for (vertex in vertices) {
            VertexHelper.put(vertex, color)
        }
        VertexHelper.end()


        releaseGl()
        glLineWidth(1f)
    }

    inline fun drawLineStrip(vertices: Array<Vec2d>, lineWidth: Float = 1f, color: ColorRGB) {
        prepareGl()
        glLineWidth(lineWidth)

        VertexHelper.begin(GL_LINE_STRIP)
        for (vertex in vertices) {
            VertexHelper.put(vertex, color)
        }
        VertexHelper.end()


        releaseGl()
        glLineWidth(1f)
    }

    inline fun drawLine(posBegin: Vec2d, posEnd: Vec2d, lineWidth: Float = 1f, color: ColorRGB) {
        prepareGl()
        glLineWidth(lineWidth)

        VertexHelper.begin(GL_LINES)
        VertexHelper.put(posBegin, color)
        VertexHelper.put(posEnd, color)
        VertexHelper.end()

        releaseGl()
        glLineWidth(1f)
    }

    inline fun getArcVertices(
        center: Vec2d,
        radius: Double,
        angleRange: Pair<Float, Float>,
        segments: Int
    ): Array<Vec2d> {
        val range = max(angleRange.first, angleRange.second) - min(angleRange.first, angleRange.second)
        val seg = calcSegments(segments, radius, range)
        val segAngle = (range.toDouble() / seg.toDouble())

        return Array(seg + 1) {
            val angle = Math.toRadians(it * segAngle + angleRange.first.toDouble())
            val unRounded = Vec2d(sin(angle), -cos(angle)).times(radius).plus(center)
            Vec2d(MathUtils.round(unRounded.x, 8), MathUtils.round(unRounded.y, 8))
        }
    }

    inline fun calcSegments(segmentsIn: Int, radius: Double, range: Float): Int {
        if (segmentsIn != -0) return segmentsIn
        val segments = radius * 0.5 * PI * (range / 360.0)
        return max(segments.roundToInt(), 16)
    }

    inline fun prepareGl() {
        GlStateUtils.texture2d(false)
        GlStateUtils.blend(true)
        GlStateUtils.smooth(true)
        GlStateUtils.lineSmooth(true)
        GlStateUtils.cull(false)
    }

    inline fun releaseGl() {
        GlStateUtils.texture2d(true)
        GlStateUtils.smooth(false)
        GlStateUtils.lineSmooth(false)
        GlStateUtils.cull(true)
    }
}