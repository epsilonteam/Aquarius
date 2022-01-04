package net.spartanb312.aquarius.manager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.spartanb312.aquarius.util.concurrent.BackgroundScope
import net.spartanb312.aquarius.util.concurrent.MainThreadExecutor
import net.spartanb312.aquarius.event.EventBus
import net.spartanb312.aquarius.event.events.client.AquariusTickEvent
import net.spartanb312.aquarius.util.TickTimer
import net.spartanb312.aquarius.util.Wrapper.mc
import net.spartanb312.aquarius.util.threads.AquariusJob
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.LinkedBlockingQueue
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

object AquariusCore {

    private val registered = CopyOnWriteArrayList<Any>()
    private val asyncTaskQueue = LinkedBlockingQueue<AquariusJob>()

    @JvmField
    var tickLength = 50

    var cachedEntities = listOf<Entity>()
    var cachedPlayerEntities = listOf<EntityPlayer>()

    private val centerThread = Thread {
        val tickTimer = TickTimer()
        while (true) {
            asyncTaskQueue.poll()?.execute()
            if (tickTimer.passed(tickLength)) {
                mc.world?.let {
                    cachedEntities = it.loadedEntityList.toList()
                    cachedPlayerEntities = it.playerEntities.toList()
                }
                AquariusTickEvent.post()
            } else if (asyncTaskQueue.size == 0) Thread.sleep(1)
        }
    }.also { it.start() }

    fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job = BackgroundScope.launch(context, start, block)

    @Suppress("DEPRECATION")
    fun addScheduledTask(mode: Executors = Executors.Aquarius, task: () -> Unit): AquariusJob {
        return AquariusJob(task).also {
            when (mode) {
                Executors.Aquarius -> {
                    asyncTaskQueue.add(it)
                    if (centerThread.isInterrupted) centerThread.resume()
                }
                Executors.Main -> MainThreadExecutor.add {
                    it.execute()
                }
                Executors.Coroutines -> BackgroundScope.launch {
                    it.execute()
                }
            }
        }
    }

    enum class Executors {
        Main,
        Aquarius,
        Coroutines
    }

    fun register(it: Any) {
        registered.add(it)
        EventBus.subscribe(it)
    }

    fun unregister(it: Any) {
        registered.add(it)
        EventBus.unsubscribe(it)
    }

}