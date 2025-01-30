@file:Suppress("unused")

package com.flare.sdk.spigot

import com.flare.sdk.platform.PlatformEntryPoint
import com.flare.sdk.spigot.player.PlayerListener
import com.flare.sdk.spigot.player.PlayerManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin


/*
 * Project: sdk
 * Created at: 30/01/2025 20:30
 * Created by: Dani-error
 */
class SpigotPlatform : PlatformEntryPoint<JavaPlugin> {

    override val playerManager: PlayerManager = PlayerManager()

    override fun setupEvents(plugin: JavaPlugin) {
        Bukkit.getPluginManager().registerEvents(PlayerListener(playerManager), plugin)
    }

}