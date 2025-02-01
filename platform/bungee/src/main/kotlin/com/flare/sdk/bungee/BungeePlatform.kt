@file:Suppress("unused")

package com.flare.sdk.bungee

import com.flare.sdk.bungee.command.CommandManager
import com.flare.sdk.bungee.player.PlayerListener
import com.flare.sdk.bungee.player.PlayerManager
import com.flare.sdk.platform.PlatformEntryPoint
import com.flare.sdk.platform.PlatformType
import net.kyori.adventure.platform.bungeecord.BungeeAudiences
import net.md_5.bungee.api.plugin.Plugin


/*
 * Project: sdk
 * Created at: 30/01/2025 20:30
 * Created by: Dani-error
 */
class BungeePlatform(platform: Plugin) : PlatformEntryPoint<Plugin>(platform) {

    override val type: PlatformType = PlatformType.BUNGEECORD

    override val playerManager: PlayerManager = PlayerManager()
    override val commandManager: CommandManager = CommandManager(this)

    override fun setupEvents() {
        platform.proxy.pluginManager.registerListener(platform, PlayerListener(playerManager))
    }

    override fun setupAdventure() {
        adventure = BungeeAudiences.create(platform)
    }

    override fun disableAdventure() {
        if (adventure != null) {
            adventure?.close()
            adventure = null
        }
    }

    companion object {
        var adventure: BungeeAudiences? = null
    }

}