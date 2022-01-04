package net.spartanb312.aquarius.event.events.client

import net.spartanb312.aquarius.event.Cancellable

class InputUpdateEvent(val key: Int, val character: Char) : Cancellable()