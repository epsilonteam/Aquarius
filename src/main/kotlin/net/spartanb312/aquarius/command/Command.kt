package net.spartanb312.aquarius.command

abstract class Command(
    val name: String,
    val prefix: String,
    val description: String,
    val syntax: String,
    val block: ExecutionScope.() -> Unit
)