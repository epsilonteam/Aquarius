package net.aquarius.client.gui.clickgui.components

import net.aquarius.client.util.Timer

class AnimatedAlphaUnit {
    var alpha = 0
    private val timer = Timer()

    fun reset() {
        alpha = 152
        timer.reset()
    }

    fun update() {
        if (timer.passed(10) && alpha > 0) {
            alpha -= 4
            timer.reset()
        }
    }
}