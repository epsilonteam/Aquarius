package net.aquarius.client.common.extensions

import net.minecraft.client.multiplayer.PlayerControllerMP
import net.aquarius.client.mixin.mixins.accessor.player.AccessorPlayerControllerMP

var PlayerControllerMP.blockHitDelay: Int
    get() = (this as AccessorPlayerControllerMP).blockHitDelay
    set(value) {
        (this as AccessorPlayerControllerMP).blockHitDelay = value
    }

val PlayerControllerMP.currentPlayerItem: Int get() = (this as AccessorPlayerControllerMP).currentPlayerItem

fun PlayerControllerMP.syncCurrentPlayItem() = (this as AccessorPlayerControllerMP).invokeSyncCurrentPlayItem()
