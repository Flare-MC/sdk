package com.flare.sdk.event.impl.player

import com.flare.sdk.event.Event
import com.flare.sdk.player.Player


/*
 * Project: sdk
 * Created at: 30/01/2025 20:44
 * Created by: Dani-error
 */
data class PlayerQuitEvent(val player: Player) : Event()