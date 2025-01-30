@file:Suppress("unused")

package com.flare.sdk.velocity

import com.flare.sdk.platform.PlatformEntryPoint
import com.flare.sdk.player.AbstractPlayerManager
import com.flare.sdk.velocity.player.PlayerManager


/*
 * Project: sdk
 * Created at: 30/01/2025 20:30
 * Created by: Dani-error
 */
class VelocityPlatform : PlatformEntryPoint {

    override val playerManager: AbstractPlayerManager<*> = PlayerManager()

}