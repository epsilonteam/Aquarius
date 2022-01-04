package net.spartanb312.aquarius.hud.huds

import net.spartanb312.aquarius.module.Category
import net.spartanb312.aquarius.hud.HUDModule
import net.spartanb312.aquarius.manager.GUIManager
import net.spartanb312.aquarius.util.graphics.font.renderer.MainFontRenderer
import net.spartanb312.aquarius.util.Wrapper

object Welcomer : HUDModule(
    name = "Welcomer",
    category = Category.HUD,
    description = "Welcome to Aquarius"
) {

    private inline val message get() = "Welcome ${Wrapper.player?.name}!Have a nice day :)"

    override fun onRender() {
        resize {
            width = MainFontRenderer.getWidth(message).toInt()
            height = MainFontRenderer.getHeight().toInt()
        }
        MainFontRenderer.drawStringWithShadow(
            message,
            x.toFloat(),
            y.toFloat(),
            GUIManager.color
        )
    }

}