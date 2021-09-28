package net.aquarius.client.management

import net.aquarius.client.common.AbstractModule
import net.aquarius.client.common.Category
import net.aquarius.client.common.interfaces.Helper
import net.aquarius.client.event.events.client.Render2DEvent
import net.aquarius.client.event.listener
import net.aquarius.client.gui.clickgui.HUDEditorScreen
import net.aquarius.client.hud.HUDModule
import net.aquarius.client.hud.huds.Welcomer
import net.aquarius.client.module.general.HUDEditor
import net.aquarius.client.module.general.RootGUI
import net.aquarius.client.module.setting.*
import net.aquarius.client.util.graphics.GlStateUtils
import net.minecraft.client.renderer.GlStateManager

object ModuleManager : Helper {

    val modules = mutableListOf<AbstractModule>()
    val hudModules = mutableListOf<HUDModule>()

    init {
        //Setting
        registerNewModule(FontSetting)
        registerNewModule(GuiSetting)
        registerNewModule(NotificationSetting)

        //General
        registerNewModule(HUDEditor)
        registerNewModule(RootGUI)

        //HUD
        registerNewModule(Welcomer)

        modules.sortBy { it.name }

        listener<Render2DEvent> {
            HUDEditorScreen.hudList.reversed().forEach {
                if (mc.currentScreen != HUDEditorScreen && it.hudModule.isEnabled) it.hudModule.onRender()
            }
        }
    }

    fun getModulesByCategory(category: Category): List<AbstractModule> {
        return modules.asSequence().filter {
            it.category == category
        }.toList()
    }

    private fun registerNewModule(abstractModule: AbstractModule) {
        modules.add(abstractModule)
        if (abstractModule.category.isHUD) {
            hudModules.add(abstractModule as HUDModule)
        }
    }

}