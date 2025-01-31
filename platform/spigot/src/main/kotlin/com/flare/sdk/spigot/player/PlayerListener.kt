package com.flare.sdk.spigot.player

import com.flare.sdk.event.impl.player.PlayerSwitchEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent


/*
 * Project: sdk
 * Created at: 30/01/2025 20:50
 * Created by: Dani-error
 */
class PlayerListener(private val playerManager: PlayerManager) : Listener {

    @EventHandler
    private fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player

        val binding = playerManager.addPlayer(player)
        com.flare.sdk.event.impl.player.PlayerJoinEvent(binding).call()
    }

    @EventHandler
    private fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player

        val binding = playerManager.removePlayer(player) ?: return

        com.flare.sdk.event.impl.player.PlayerQuitEvent(binding).call()
    }

}