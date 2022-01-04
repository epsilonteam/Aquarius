package net.spartanb312.aquarius.command.commands

import net.spartanb312.aquarius.Aquarius.VERSION
import net.spartanb312.aquarius.command.Command
import net.spartanb312.aquarius.command.execute
import net.spartanb312.aquarius.manager.CommandManager
import net.spartanb312.aquarius.manager.ModuleManager
import net.spartanb312.aquarius.module.general.RootGUI
import net.spartanb312.aquarius.notification.NotificationManager
import net.spartanb312.aquarius.util.ChatUtil.AQUA
import net.spartanb312.aquarius.util.ChatUtil.DARK_AQUA
import net.spartanb312.aquarius.util.ChatUtil.GOLD
import net.spartanb312.aquarius.util.ChatUtil.GREEN
import net.spartanb312.aquarius.util.ChatUtil.YELLOW

object Help : Command(
    name = "help",
    prefix = "help",
    description = "Get helps",
    syntax = "help",
    block = {

        execute { moduleName ->
            ModuleManager.modules.find { it.name.equals(moduleName, true) }?.let {
                NotificationManager.info("[${it.name}]${it.description}")
                return@execute
            }
        }

        NotificationManager.info("${AQUA}Aquarius ${DARK_AQUA}V$VERSION")
        NotificationManager.info("${AQUA}Author ${DARK_AQUA}B312")
        NotificationManager.info("${AQUA}CommandPrefix ${DARK_AQUA}${CommandManager.commandPrefix}")
        NotificationManager.info("${AQUA}ClickGUI ${DARK_AQUA}${RootGUI.keyBind.displayValue}")
        NotificationManager.info("${GOLD}Available Commands : ")
        CommandManager.commands.forEach {
            NotificationManager.info("${GOLD}${it.name} ${YELLOW}${it.syntax} ${GREEN}(${it.description})")
        }

    }
)