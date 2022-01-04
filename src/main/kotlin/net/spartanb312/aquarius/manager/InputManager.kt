package net.spartanb312.aquarius.manager

import net.spartanb312.aquarius.util.common.key.KeyBind
import org.lwjgl.input.Keyboard

object InputManager {

    private val listeners = mutableListOf<KeyBind>()

    fun KeyBind.register() {
        listeners.add(this)
    }

    fun KeyBind.unregister() {
        listeners.remove(this)
    }

    @JvmStatic
    fun onKey(key: Int) {
        AquariusCore.addScheduledTask {
            listeners.forEach {
                if (it.key.size > 1) {
                    if (it.key.any { it2 ->
                            it2 == key
                        } && it.key.all { it3 ->
                            Keyboard.isKeyDown(it3)
                        }) AquariusCore.addScheduledTask(AquariusCore.Executors.Main) {
                        it.action.invoke()
                    }
                } else if (it.key[0] == key) AquariusCore.addScheduledTask(AquariusCore.Executors.Main) {
                    it.action.invoke()
                }
            }
        }
    }

}