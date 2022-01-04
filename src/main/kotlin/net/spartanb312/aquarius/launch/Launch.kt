package net.spartanb312.aquarius.launch

annotation class Launch(val mixinFile: String = "") {
    annotation class Tweak
    annotation class PreInit
    annotation class Init
    annotation class PostInit
    annotation class Ready
}