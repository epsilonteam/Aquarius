package net.aquarius.client.management

import net.aquarius.client.common.key.KeyBind
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
        listeners.forEach {
            if (it.key.size > 1) {
                if (it.key.any { it2 ->
                        it2 == key
                    } && it.key.all { it3 ->
                        Keyboard.isKeyDown(it3)
                    }) it.action.invoke()
            } else if (it.key[0] == key) {
                it.action.invoke()
            }
        }
    }

}