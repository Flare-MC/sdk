@file:Suppress("unused")

package com.flare.sdk.spigot

import com.flare.sdk.platform.PlatformEntryPoint
import com.flare.sdk.player.AbstractPlayerManager
import com.flare.sdk.spigot.player.PlayerManager


/*
 * Project: sdk
 * Created at: 30/01/2025 20:30
 * Created by: Dani-error
 */
class SpigotPlatform : PlatformEntryPoint {

    override val playerManager: AbstractPlayerManager<*> = PlayerManager()

}