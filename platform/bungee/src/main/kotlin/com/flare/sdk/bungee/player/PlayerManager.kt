package com.flare.sdk.bungee.player

import com.flare.sdk.bungee.BungeePlatform
import com.flare.sdk.player.AbstractPlayerManager
import net.kyori.adventure.platform.bungeecord.BungeeAudiences
import net.kyori.adventure.text.Component
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.ProxiedPlayer
import java.util.*


/*
 * Project: sdk
 * Created at: 30/01/2025 20:32
 * Created by: Dani-error
 */
class PlayerManager() : AbstractPlayerManager<ProxiedPlayer>() {
    override fun bindPlayer(player: ProxiedPlayer): com.flare.sdk.player.Player {
        return object : com.flare.sdk.player.Player {

            override val uuid: UUID
                get() = player.uniqueId

            override val name: String
                get() = player.name

            override fun sendMessage(message: Component) {
                BungeePlatform.adventure?.player(player)?.sendMessage(message)
            }

            override fun sendMessage(message: String) {
                player.sendMessage(*TextComponent.fromLegacyText(message))
            }

            override fun sendTranslatedMessage(message: String) {
                player.sendMessage(*TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', message)))
            }

        }
    }
}
