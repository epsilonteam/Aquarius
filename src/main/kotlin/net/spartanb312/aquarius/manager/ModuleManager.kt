package net.spartanb312.aquarius.manager

import net.spartanb312.aquarius.Aquarius.SCAN_GROUP
import net.spartanb312.aquarius.module.AbstractModule
import net.spartanb312.aquarius.module.Category
import net.spartanb312.aquarius.util.common.interfaces.Helper
import net.spartanb312.aquarius.event.events.client.Render2DEvent
import net.spartanb312.aquarius.event.listener
import net.spartanb312.aquarius.gui.clickgui.HUDEditorScreen
import net.spartanb312.aquarius.hud.HUDModule
import net.spartanb312.aquarius.util.ClassUtils.findTypedClasses
import net.spartanb312.aquarius.util.ClassUtils.instance
import net.spartanb312.aquarius.util.Logger

object ModuleManager : Helper {

    val modules = mutableListOf<AbstractModule>()
    val hudModules = mutableListOf<HUDModule>()

    init {
        Logger.info("Initializing ModuleManager")
        scanModules()
        listener<Render2DEvent> {
            HUDEditorScreen.hudList.reversed().forEach {
                if (mc.currentScreen != HUDEditorScreen && it.hudModule.isEnabled) it.hudModule.onRender()
            }
        }
    }

    private fun scanModules() {
        SCAN_GROUP.findTypedClasses<AbstractModule> {
            !(startsWith("java.")
                    || startsWith("sun")
                    || startsWith("org.lwjgl")
                    || startsWith("org.apache.logging")
                    || startsWith("net.minecraft")
                    || contains("mixin")
                    || contains("gui"))
        }.forEach {
            kotlin.runCatching {
                it.instance?.let { instance -> registerNewModule(instance) }
            }
        }
        modules.sortBy { it.name }
        hudModules.sortBy { it.name }
    }

    private fun registerNewModule(abstractModule: AbstractModule) {
        modules.add(abstractModule)
        if (abstractModule.category.isHUD) {
            hudModules.add(abstractModule as HUDModule)
        }
    }

    fun getModulesByCategory(category: Category): List<AbstractModule> {
        return modules.asSequence().filter {
            it.category == category
        }.toList()
    }

}