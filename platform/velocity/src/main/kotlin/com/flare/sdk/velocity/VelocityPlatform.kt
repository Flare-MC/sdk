@file:Suppress("unused")

package com.flare.sdk.velocity

import com.flare.sdk.FlareException
import com.flare.sdk.platform.PlatformEntryPoint
import com.flare.sdk.velocity.player.PlayerListener
import com.flare.sdk.velocity.player.PlayerManager
import com.velocitypowered.api.proxy.ProxyServer

/*
 * Project: sdk
 * Created at: 30/01/2025 20:30
 * Created by: Dani-error
 */
class VelocityPlatform : PlatformEntryPoint<Any> {

    override val playerManager: PlayerManager = PlayerManager()

    override fun setupEvents(plugin: Any) {
        try {
            val serverField = plugin.javaClass.getDeclaredField("server")
            serverField.isAccessible = true
            val proxyServer = serverField.get(plugin) as ProxyServer

            proxyServer.eventManager.register(plugin, PlayerListener(playerManager))
        } catch (e: Exception) {
            throw FlareException("Couldn't setup the event conversion system... ${e.message}")
        }
    }

}