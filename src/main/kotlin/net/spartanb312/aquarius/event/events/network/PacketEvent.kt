package net.spartanb312.aquarius.event.events.network

import net.spartanb312.aquarius.event.Cancellable
import net.minecraft.network.Packet

open class PacketEvent(val packet: Packet<*>) : Cancellable() {
    class Send(packet: Packet<*>) : PacketEvent(packet)
    class Receive(packet: Packet<*>) : PacketEvent(packet)
}
