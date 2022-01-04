package net.spartanb312.aquarius.util.common.extensions

import java.io.File

class ListScope<T> {
    val mutableList = mutableListOf<T>()
    fun yield(value: T) = mutableList.add(value)
}

fun <T> list(block: ListScope<T>.() -> Unit): List<T> = ListScope<T>().let {
    it.block()
    it.mutableList
}

fun File.isNotExist(): Boolean {
    return !this.exists()
}

fun Any.runSafe(ignoreException: Boolean = true, function: () -> Unit): Boolean {
    return try {
        function.invoke()
        true
    } catch (exception: Exception) {
        if (!ignoreException) exception.printStackTrace()
        false
    }
}

fun <T : Number> T.isPositive(): Boolean {
    return this.toDouble() >= 0
}

fun <T : Number> T.isNegative(): Boolean {
    return this.toDouble() < 0
}

fun Boolean.toInt(): Int {
    return if (this) 1 else 0
}