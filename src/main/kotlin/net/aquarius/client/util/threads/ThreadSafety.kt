package net.aquarius.client.util.threads

import net.aquarius.client.event.events.client.SafeClientEvent
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
suspend fun <R> runSafeSuspend(block: suspend SafeClientEvent.() -> R): R? {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }

    return SafeClientEvent.instance?.block()
}

@OptIn(ExperimentalContracts::class)
fun <R> runSafe(block: SafeClientEvent.() -> R): R? {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }

    return SafeClientEvent.instance?.block()
}