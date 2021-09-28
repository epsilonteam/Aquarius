package net.aquarius.client.util.graphics

import net.minecraft.client.Minecraft
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.GL11
import kotlin.math.min
import kotlin.math.sqrt

@Suppress("SameParameterValue")
open class ParticleSystem(initAmount: Int) {
    private val particleList: MutableList<Particle> = ArrayList()
    private fun addParticles(n: Int) {
        for (i in 0 until n) {
            particleList.add(Particle.generateParticle())
        }
    }

    fun tick(delta: Int) {
        for (particle in particleList) {
            particle.tick(delta, SPEED)
        }
    }

    private fun drawLine(f: Float, f2: Float, f3: Float, f4: Float, f5: Float, f6: Float, f7: Float, f8: Float) {
        GL11.glColor4f(f5, f6, f7, f8)
        GL11.glLineWidth(0.5f)
        GL11.glBegin(1)
        GL11.glVertex2f(f, f2)
        GL11.glVertex2f(f3, f4)
        GL11.glEnd()
    }

    fun render() {
        GL11.glPushMatrix()
        GL11.glEnable(3042)
        GL11.glDisable(3553)
        GL11.glBlendFunc(770, 771)
        GL11.glDisable(2884)
        GL11.glDisable(3553)
        GL11.glDisable(2929)
        GL11.glDepthMask(false)
        if (Minecraft.getMinecraft().currentScreen == null) {
            return
        }
        for (particle in particleList) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, particle.alpha / 255.0f)
            GL11.glPointSize(particle.size)
            GL11.glBegin(0)
            GL11.glVertex2f(particle.x, particle.y)
            GL11.glEnd()
            val width =
                Mouse.getEventX() * Minecraft.getMinecraft().currentScreen!!.width / Minecraft.getMinecraft().displayWidth
            val height =
                Minecraft.getMinecraft().currentScreen!!.height - Mouse.getEventY() * Minecraft.getMinecraft().currentScreen!!.height / Minecraft.getMinecraft().displayHeight - 1
            var nearestDistance = 0.0f
            var nearestParticle: Particle? = null
            val dist = 100
            for (particle1 in particleList) {
                val distance = particle.getDistanceTo(particle1)
                if (distance > dist || distance(
                        width.toFloat(),
                        height.toFloat(),
                        particle.x,
                        particle.y
                    ) > dist && distance(
                        width.toFloat(),
                        height.toFloat(),
                        particle1.x,
                        particle1.y
                    ) > dist || nearestDistance > 0.0f && distance > nearestDistance
                ) {
                    continue
                }
                nearestDistance = distance
                nearestParticle = particle1
            }
            if (nearestParticle == null) {
                continue
            }
            val alpha = min(1.0f, min(1.0f, 1.0f - nearestDistance / dist))
            drawLine(particle.x, particle.y, nearestParticle.x, nearestParticle.y, 1.0f, 1.0f, 1.0f, alpha)
        }
        GL11.glPushMatrix()
        GL11.glTranslatef(0.5f, 0.5f, 0.5f)
        GL11.glNormal3f(0.0f, 1.0f, 0.0f)
        GL11.glEnable(3553)
        GL11.glPopMatrix()
        GL11.glDepthMask(true)
        GL11.glEnable(2884)
        GL11.glEnable(3553)
        GL11.glEnable(2929)
        GL11.glEnable(3553)
        GL11.glDisable(3042)
        GL11.glPopMatrix()
    }

    companion object {
        private const val SPEED = 0.1f
        @JvmStatic
        fun distance(x: Float, y: Float, x1: Float, y1: Float): Double {
            return sqrt(((x - x1) * (x - x1) + (y - y1) * (y - y1)).toDouble())
        }
    }

    init {
        addParticles(initAmount)
    }
}