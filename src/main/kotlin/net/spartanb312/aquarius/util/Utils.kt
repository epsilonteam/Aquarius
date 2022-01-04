package net.spartanb312.aquarius.util

import net.minecraft.client.Minecraft
import net.minecraft.client.shader.ShaderGroup

@Suppress("NOTHING_TO_INLINE")
object Utils {

    inline fun <E : Enum<E>> E.next(): E = declaringClass.enumConstants.run {
        get((ordinal + 1) % size)
    }

    inline fun <E : Enum<E>> E.last(): E = declaringClass.enumConstants.run {
        get((ordinal - 1) % size)
    }

    //Minecraft is gay
    inline fun getShaderGroup(): ShaderGroup? = Minecraft.getMinecraft().entityRenderer.shaderGroup

}