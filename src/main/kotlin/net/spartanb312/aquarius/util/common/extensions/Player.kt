package net.spartanb312.aquarius.util.common.extensions

import net.minecraft.client.multiplayer.PlayerControllerMP
import net.spartanb312.aquarius.mixin.mixins.accessor.player.AccessorPlayerControllerMP

var PlayerControllerMP.blockHitDelay: Int
    get() = (this as AccessorPlayerControllerMP).blockHitDelay
    set(value) {
        (this as AccessorPlayerControllerMP).blockHitDelay = value
    }

val PlayerControllerMP.currentPlayerItem: Int get() = (this as AccessorPlayerControllerMP).currentPlayerItem

fun PlayerControllerMP.syncCurrentPlayItem() = (this as AccessorPlayerControllerMP).invokeSyncCurrentPlayItem()
