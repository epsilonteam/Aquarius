package net.spartanb312.aquarius.command.commands

import net.spartanb312.aquarius.command.Command
import net.spartanb312.aquarius.command.execute
import net.spartanb312.aquarius.manager.CommandManager
import net.spartanb312.aquarius.notification.NotificationManager
import net.spartanb312.aquarius.util.Wrapper

object TP : Command(
    name = "TP",
    prefix = "tp",
    description = "Teleport you to the coords",
    syntax = "tp <x> <y> <z>",
    block = {
        try {
            execute { x ->
                execute { y ->
                    execute { z ->
                        Wrapper.mc.player?.setPosition(x.toDouble(), y.toDouble(), z.toDouble())
                        NotificationManager.info("Teleported you to $x $y $z")
                    }
                }
            }
        } catch (ignore: Exception) {
            NotificationManager.error("Usage : ${TP.syntax}")
        }
    }
)