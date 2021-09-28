package net.aquarius.client.hud.huds

import net.aquarius.client.common.Category
import net.aquarius.client.hud.HUDModule
import net.aquarius.client.management.GUIManager
import net.aquarius.client.util.graphics.font.renderer.MainFontRenderer
import net.aquarius.client.util.Wrapper

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