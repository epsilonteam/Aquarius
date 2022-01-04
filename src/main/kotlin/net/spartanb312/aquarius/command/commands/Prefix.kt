package net.spartanb312.aquarius.command.commands

import net.spartanb312.aquarius.command.Command
import net.spartanb312.aquarius.command.execute
import net.spartanb312.aquarius.manager.CommandManager
import net.spartanb312.aquarius.notification.NotificationManager

object Prefix : Command(
    name = "Prefix",
    prefix = "prefix",
    description = "Change prefix",
    syntax = "prefix <prefix>",
    block = {
        execute {
            CommandManager.commandPrefix = it
            NotificationManager.info("Changed prefix to $it")
        }
    }
)