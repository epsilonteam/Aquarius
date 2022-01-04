package net.spartanb312.aquarius.notification

import net.spartanb312.aquarius.module.AbstractModule
import net.spartanb312.aquarius.util.ChatUtil

object NotificationManager {

    fun info(msg: String) = ChatUtil.printChatMessage(msg)

    fun warn(msg: String) = ChatUtil.printErrorChatMessage(msg)

    fun error(msg: String) = ChatUtil.printErrorChatMessage(msg)

    fun fatal(msg: String) {

    }

    fun debug(msg: String) {

    }

    fun totemPop(name: String, count: Int) {

    }

    fun poppedDeath(name: String, count: Int) {

    }

    fun moduleToggle(module: AbstractModule, toggled: Boolean) {

    }

}