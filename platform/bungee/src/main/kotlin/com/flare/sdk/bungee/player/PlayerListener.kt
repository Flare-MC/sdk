package com.flare.sdk.bungee.player

import com.flare.sdk.event.impl.player.PlayerJoinEvent
import com.flare.sdk.event.impl.player.PlayerQuitEvent
import com.flare.sdk.event.impl.player.PlayerSwitchEvent
import net.md_5.bungee.api.event.PlayerDisconnectEvent
import net.md_5.bungee.api.event.PostLoginEvent
import net.md_5.bungee.api.event.ServerSwitchEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler


/*
 * Project: sdk
 * Created at: 30/01/2025 21:35
 * Created by: Dani-error
 */
class PlayerListener(private val playerManager: PlayerManager) : Listener {

    @EventHandler
    private fun onPlayerJoin(event: PostLoginEvent) {
        val player = event.player

        val binding = playerManager.addPlayer(player)
        PlayerJoinEvent(event, binding).call()
    }

    @EventHandler
    private fun onPlayerSwitch(event: ServerSwitchEvent) {
        val player = event.player

        val binding = playerManager.getPlayer(player) ?: return
        PlayerSwitchEvent(event, binding).call()
    }

    @EventHandler
    private fun onPlayerQuit(event: PlayerDisconnectEvent) {
        val player = event.player

        val binding = playerManager.removePlayer(player) ?: return

        PlayerQuitEvent(event, binding).call()
    }

}