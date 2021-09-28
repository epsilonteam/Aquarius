package net.aquarius.client.launch.api

import net.aquarius.client.management.SpartanBus

interface Loadable {
    fun preInit() {}
    fun postInit() {}

    fun register(any: Any) {
        SpartanBus.register(any)
    }
}