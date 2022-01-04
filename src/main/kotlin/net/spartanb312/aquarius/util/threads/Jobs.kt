package net.spartanb312.aquarius.util.threads

import java.util.concurrent.atomic.AtomicBoolean
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

class AquariusJob(private val task: () -> Unit) {

    private val finished = AtomicBoolean(false)

    fun execute() = finished.execute(task)

    @Synchronized
    fun isFinished() = finished.get()

}

@OptIn(ExperimentalContracts::class)
inline fun AtomicBoolean.execute(block: () -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    block.invoke()
    set(true)
}