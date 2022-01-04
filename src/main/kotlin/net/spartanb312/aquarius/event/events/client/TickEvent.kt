package net.spartanb312.aquarius.event.events.client

import net.spartanb312.aquarius.event.Event
import net.spartanb312.aquarius.event.ProfilerEvent

object ClientTickEvent : ProfilerEvent("AquariusClientTick")

object RenderTickEvent : ProfilerEvent("AquariusRenderTick")

object AquariusTickEvent : Event()