package net.spartanb312.aquarius.event.events.client

import net.spartanb312.aquarius.event.Cancellable

class ChatEvent(var message: String) : Cancellable()