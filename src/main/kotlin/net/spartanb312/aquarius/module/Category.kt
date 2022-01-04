package net.spartanb312.aquarius.module

enum class Category(val showName: String, val isHUD: Boolean = false) {

    //Cheat
    Client("Client"),
    Combat("Combat"),
    Misc("Misc"),
    Movement("Movement"),
    Player("Player"),
    Render("Render"),

    //HUD Editor
    HUD("HUD", true),

    //Hidden
    Hidden("Hidden"),
    Setting("Setting")

}
