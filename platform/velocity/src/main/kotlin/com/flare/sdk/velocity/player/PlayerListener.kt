package com.flare.sdk.velocity.player

import com.flare.sdk.event.impl.player.PlayerJoinEvent
import com.flare.sdk.event.impl.player.PlayerSwitchEvent
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.connection.PostLoginEvent
import com.velocitypowered.api.event.player.ServerConnectedEvent


/*
 * Project: sdk
 * Created at: 30/01/2025 21:40
 * Created by: Dani-error
 */
class PlayerListener(private val playerManager: PlayerManager) {

    @Subscribe
    fun onPlayerJoin(event: PostLoginEvent) {
        val player = event.player

        val binding = playerManager.addPlayer(player)
        PlayerJoinEvent(event, binding).call()
    }

    @Subscribe
    fun onPlayerSwitch(event: ServerConnectedEvent) {
        val player = event.player

        val binding = playerManager.getPlayer(player) ?: return
        PlayerSwitchEvent(event, binding).call()
    }

    @Subscribe
    fun onPlayerQuit(event: DisconnectEvent) {
        val player = event.player

        val binding = playerManager.removePlayer(player) ?: return

        com.flare.sdk.event.impl.player.PlayerQuitEvent(event, binding).call()
    }

}