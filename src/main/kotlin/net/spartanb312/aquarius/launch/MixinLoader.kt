package net.spartanb312.aquarius.launch

import net.spartanb312.aquarius.util.SafeLogger
import org.spongepowered.asm.launch.MixinBootstrap
import org.spongepowered.asm.mixin.MixinEnvironment
import org.spongepowered.asm.mixin.Mixins

object MixinLoader {

    private val log = SafeLogger("MIXIN")

    val mixins = mutableListOf<String>()

    fun load() {
        MixinBootstrap.init()
        mixins.forEach {
            log.info("Initializing mixin $it")
            Mixins.addConfiguration(it)
        }
        MixinEnvironment.getDefaultEnvironment().obfuscationContext = "searge"
        MixinEnvironment.getDefaultEnvironment().side = MixinEnvironment.Side.CLIENT
        log.info("Aquarius MixinLoader all mixins initialized")
        log.info(MixinEnvironment.getDefaultEnvironment().obfuscationContext)
    }

}