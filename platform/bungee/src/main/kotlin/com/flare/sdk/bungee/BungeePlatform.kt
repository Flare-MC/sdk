@file:Suppress("unused")

package com.flare.sdk.bungee

import com.flare.sdk.bungee.player.PlayerListener
import com.flare.sdk.bungee.player.PlayerManager
import com.flare.sdk.platform.PlatformEntryPoint
import com.flare.sdk.player.AbstractPlayerManager
import net.md_5.bungee.api.plugin.Plugin


/*
 * Project: sdk
 * Created at: 30/01/2025 20:30
 * Created by: Dani-error
 */
class BungeePlatform : PlatformEntryPoint<Plugin> {

    override val playerManager: PlayerManager = PlayerManager()

    override fun setupEvents(plugin: Plugin) {
        plugin.proxy.pluginManager.registerListener(plugin, PlayerListener(playerManager))
    }

}