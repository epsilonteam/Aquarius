package net.aquarius.client.management

import net.aquarius.client.event.EventBus
import java.util.concurrent.CopyOnWriteArrayList

@Suppress("NOTHING_TO_INLINE")
object SpartanBus {

    val bus = CopyOnWriteArrayList<Any>()

    inline fun register(it:Any){
        bus.add(it)
        EventBus.subscribe(it)
    }

    inline fun unregister(it:Any){
        bus.add(it)
        EventBus.unsubscribe(it)
    }

}