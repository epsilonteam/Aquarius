package net.spartanb312.aquarius.manager

import net.minecraft.network.play.client.CPacketChatMessage
import net.spartanb312.aquarius.Aquarius.DEFAULT_COMMAND_PREFIX
import net.spartanb312.aquarius.Aquarius.SCAN_GROUP
import net.spartanb312.aquarius.command.Command
import net.spartanb312.aquarius.command.CommandScope
import net.spartanb312.aquarius.command.ExecutionScope
import net.spartanb312.aquarius.event.events.client.ChatEvent
import net.spartanb312.aquarius.event.events.network.PacketEvent
import net.spartanb312.aquarius.event.listener
import net.spartanb312.aquarius.mixin.mixins.accessor.network.AccessorCPacketChatMessage
import net.spartanb312.aquarius.util.ClassUtils.findTypedClasses
import net.spartanb312.aquarius.util.ClassUtils.instance
import net.spartanb312.aquarius.util.Logger
import net.spartanb312.aquarius.util.common.interfaces.Helper
import java.util.*

object CommandManager : Helper {

    val commands = mutableListOf<Command>()
    var commandPrefix = DEFAULT_COMMAND_PREFIX

    init {
        Logger.info("Initializing CommandManager")
        SCAN_GROUP.findTypedClasses<Command> {
            !(startsWith("java.")
                    || startsWith("sun")
                    || startsWith("org.lwjgl")
                    || startsWith("org.apache.logging")
                    || startsWith("net.minecraft")
                    || contains("mixin")
                    || contains("gui"))
        }.forEach {
            kotlin.runCatching {
                it.instance?.let { instance -> commands.add(instance) }
            }
        }
        commands.sortBy { it.name }

        listener<PacketEvent.Send> {
            if (it.packet is CPacketChatMessage) {
                if (it.packet.message.runCommand()) it.cancel()
                ChatEvent(it.packet.message).let { event ->
                    event.post()
                    if (event.cancelled) it.cancel()
                    else (it.packet as AccessorCPacketChatMessage).setMessage(event.message)
                }
            }
        }
    }

    private fun String.runCommand(): Boolean {
        val cachedPrefix = commandPrefix
        if (!this.startsWith(cachedPrefix)) return false
        val queue = LinkedList<String>()
        removePrefix(cachedPrefix).split(" ").forEach { queue.add(it) }
        queue.poll()?.let {
            commands.forEach { command ->
                if (command.prefix.equals(it, true)) {
                    command.block(ExecutionScope(queue.toList().toTypedArray(), command))
                    return true
                }
            }
        }
        return false
    }

}