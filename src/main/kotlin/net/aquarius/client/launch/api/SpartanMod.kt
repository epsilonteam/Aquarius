package net.aquarius.client.launch.api

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class SpartanMod(
    val name: String = "",
    val id: String,
    val version: String = "",
    val description: String,
    val mixinFile: String = ""
)
