package com.flare.sdk.spigot.player

import com.flare.sdk.player.AbstractPlayerManager
import com.flare.sdk.spigot.SpigotPlatform
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.text.Component
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.entity.Player
import java.util.*


/*
 * Project: sdk
 * Created at: 30/01/2025 20:32
 * Created by: Dani-error
 */
class PlayerManager() : AbstractPlayerManager<Player>() {
    override fun bindPlayer(player: Player): com.flare.sdk.player.Player {
        return object : com.flare.sdk.player.Player {

            override val uuid: UUID
                get() = player.uniqueId

            override val name: String
                get() = player.name

            override fun sendMessage(message: Component) {
                SpigotPlatform.adventure?.player(player)?.sendMessage(message)
            }

            override fun sendMessage(message: String) {
                player.sendMessage(message)
            }

            override fun sendTranslatedMessage(message: String) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', message))
            }

        }
    }
}