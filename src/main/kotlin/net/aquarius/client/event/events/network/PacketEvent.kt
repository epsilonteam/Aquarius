package net.aquarius.client.event.events.network

import net.aquarius.client.event.Cancellable
import net.minecraft.network.Packet

open class PacketEvent(val packet: Packet<*>) : Cancellable() {
    class Send(packet: Packet<*>) : PacketEvent(packet)
    class Receive(packet: Packet<*>) : PacketEvent(packet)
}
