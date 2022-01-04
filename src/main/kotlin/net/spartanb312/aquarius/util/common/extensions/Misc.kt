package net.spartanb312.aquarius.util.common.extensions

import net.minecraft.client.Minecraft
import net.minecraft.entity.Entity
import net.minecraft.item.ItemTool
import net.minecraft.util.Timer
import net.spartanb312.aquarius.mixin.mixins.accessor.AccessorEntity
import net.spartanb312.aquarius.mixin.mixins.accessor.AccessorItemTool
import net.spartanb312.aquarius.mixin.mixins.accessor.AccessorMinecraft
import net.spartanb312.aquarius.mixin.mixins.accessor.AccessorTimer

val Entity.isInWeb: Boolean get() = (this as AccessorEntity).isInWeb

val ItemTool.attackDamage get() = (this as AccessorItemTool).attackDamage

val Minecraft.timer: Timer get() = (this as AccessorMinecraft).timer
val Minecraft.renderPartialTicksPaused: Float get() = (this as AccessorMinecraft).renderPartialTicksPaused
var Minecraft.rightClickDelayTimer: Int
    get() = (this as AccessorMinecraft).rightClickDelayTimer
    set(value) {
        (this as AccessorMinecraft).rightClickDelayTimer = value
    }

fun Minecraft.rightClickMouse() = (this as AccessorMinecraft).invokeRightClickMouse()

fun Minecraft.sendClickBlockToController(leftClick: Boolean) =
    (this as AccessorMinecraft).invokeSendClickBlockToController(leftClick)

var Timer.tickLength: Float
    get() = (this as AccessorTimer).tickLength
    set(value) {
        (this as AccessorTimer).tickLength = value
    }