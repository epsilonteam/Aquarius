package net.aquarius.client.launch

import net.aquarius.client.Aquarius
import net.aquarius.client.event.EventBus
import net.aquarius.client.launch.api.Loadable
import net.aquarius.client.launch.api.SpartanMod

object InitManager {

    private val initializedMod = mutableMapOf<Loadable, SpartanMod>()

    init {
        init(Aquarius)
    }

    @JvmStatic
    fun init(mod: Loadable) {
        initializedMod[mod] = mod::class.java.getAnnotation(SpartanMod::class.java)
    }

    fun load() {
        initializedMod.forEach { (_, annotation) ->
            if (annotation.mixinFile != "") {
                MixinLoader.mixins.add(annotation.mixinFile)
            }
            FMLCoreMod.log.info("Find Spartan Mod ${annotation.name}")
        }
    }

    fun onMinecraftInit() {
        initializedMod.forEach {
            EventBus.subscribe(it)
        }
    }

    @JvmStatic
    fun preInitHook() {
        initializedMod.keys.forEach(Loadable::preInit)
    }

    @JvmStatic
    fun postInitHook() {
        initializedMod.keys.forEach(Loadable::postInit)
    }

}