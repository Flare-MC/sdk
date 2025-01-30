package com.flare.sdk.platform

import com.flare.sdk.player.AbstractPlayerManager


/*
 * Project: sdk
 * Created at: 30/01/2025 20:28
 * Created by: Dani-error
 */
interface PlatformEntryPoint {

    val playerManager: AbstractPlayerManager<*>

}