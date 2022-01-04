package net.spartanb312.aquarius.util.concurrent

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.function.LongSupplier

@OptIn(ObsoleteCoroutinesApi::class)
object BackgroundScope : CoroutineScope by CoroutineScope(
    newFixedThreadPoolContext(
        Runtime.getRuntime().availableProcessors(),
        "Background scope"
    )
)

fun runBackground(task: () -> Unit) {
    BackgroundScope.launch {
        task.invoke()
    }
}

fun runDelay(delayMs: Long, task: () -> Unit) {
    BackgroundScope.launch {
        delay(delayMs)
        task.invoke()
    }
}

fun runRepeat(delayMs: Long = 1000L, task: () -> Unit, suspended: Boolean = false): RepeatUnit {
    return RepeatUnit(
        delayMs = delayMs,
        task = task,
        scope = BackgroundScope,
        suspended = AtomicBoolean(suspended)
    )
}

fun runRepeat(delaySupplier: LongSupplier, task: () -> Unit, suspended: Boolean = false): RepeatUnit {
    return RepeatUnit(
        delaySupplier = delaySupplier,
        task = task,
        scope = BackgroundScope,
        suspended = AtomicBoolean(suspended)
    )
}

fun <T> T.letDesync(task: (T) -> Unit) {
    BackgroundScope.launch {
        task.invoke(this@letDesync)
    }
}
