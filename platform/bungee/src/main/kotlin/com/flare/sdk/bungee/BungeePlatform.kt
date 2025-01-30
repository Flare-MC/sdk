@file:Suppress("unused")

package com.flare.sdk.bungee

import com.flare.sdk.bungee.player.PlayerManager
import com.flare.sdk.platform.PlatformEntryPoint
import com.flare.sdk.player.AbstractPlayerManager


/*
 * Project: sdk
 * Created at: 30/01/2025 20:30
 * Created by: Dani-error
 */
class BungeePlatform : PlatformEntryPoint {

    override val playerManager: AbstractPlayerManager<*> = PlayerManager()

}