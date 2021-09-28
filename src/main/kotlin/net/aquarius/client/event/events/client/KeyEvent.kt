package net.aquarius.client.event.events.client

import net.aquarius.client.event.Cancellable

class KeyEvent(val key: Int, val character: Char) : Cancellable()