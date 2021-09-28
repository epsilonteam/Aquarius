package net.aquarius.client.event.events.client

import net.aquarius.client.event.Cancellable

class InputUpdateEvent(val key: Int, val character: Char) : Cancellable()