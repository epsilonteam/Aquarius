package net.spartanb312.aquarius.launch

import net.spartanb312.aquarius.Aquarius.SCAN_GROUP
import net.spartanb312.aquarius.util.ClassUtils.getAnnotatedClasses

object InitializationManager {

    private val mods = mutableSetOf<Any>()

    init {
        SCAN_GROUP.getAnnotatedClasses<Launch> {
            !(startsWith("java.")
                    || startsWith("sun")
                    || startsWith("org.lwjgl")
                    || startsWith("org.apache.logging")
                    || startsWith("net.minecraft")
                    || contains("mixin")
                    || contains("gui"))
        }.forEach {
            kotlin.runCatching {
                mods.add(it.instance)
            }
        }
    }

    @JvmStatic
    fun onTweak() {
        mods.forEach {
            if (it::class.java.isAnnotationPresent(Launch::class.java)) {
                it::class.java.getAnnotation(Launch::class.java).mixinFile.apply {
                    if (this != "") MixinLoader.mixins.add(this)
                }
            }
        }
        mods.forEach { it::class.java.invokeMarkedMethod<Launch.Tweak>() }
    }

    @JvmStatic
    fun preInit() = mods.forEach { it::class.java.invokeMarkedMethod<Launch.PreInit>() }

    @JvmStatic
    fun onInit() = mods.forEach { it::class.java.invokeMarkedMethod<Launch.Init>() }

    @JvmStatic
    fun postInit() = mods.forEach { it::class.java.invokeMarkedMethod<Launch.PostInit>() }

    @JvmStatic
    fun onReady() = mods.forEach { it::class.java.invokeMarkedMethod<Launch.Ready>() }

    private inline fun <reified T> Class<*>.invokeMarkedMethod() where T : Annotation {
        this.declaredMethods.filter { it.isAnnotationPresent(T::class.java) }
            .forEach {
                it.isAccessible = true
                it.invoke(this.instance)
            }
    }

    @Suppress("UNCHECKED_CAST")
    private inline val <T> Class<out T>.instance
        get() = this.getDeclaredField("INSTANCE")[null] as T

}