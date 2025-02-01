package com.flare.sdk.bungee.command

import com.flare.sdk.bungee.BungeePlatform
import net.kyori.adventure.platform.bungeecord.BungeeAudiences
import net.kyori.adventure.text.Component
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.TextComponent

/*
 * Project: sdk
 * Created at: 1/2/25 19:39
 * Created by: Dani-error
 */
class CommandSenderWrapper(private val sender: CommandSender) : com.flare.sdk.command.CommandSender {

    override val name: String = sender.name

    override fun sendMessage(message: Component) {
        BungeePlatform.adventure?.sender(sender)?.sendMessage(message)
    }

    override fun sendMessage(message: String) {
        sender.sendMessage(*TextComponent.fromLegacyText(message))
    }

    override fun sendTranslatedMessage(message: String) {
        sender.sendMessage(*TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', message)))
    }

}