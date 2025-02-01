package com.flare.sdk.spigot.command

import com.flare.sdk.command.CommandSender
import com.flare.sdk.spigot.SpigotPlatform
import net.kyori.adventure.text.Component
import net.md_5.bungee.api.ChatColor


/*
 * Project: sdk
 * Created at: 1/2/25 19:39
 * Created by: Dani-error
 */
class CommandSenderWrapper(private val sender: org.bukkit.command.CommandSender) : CommandSender {

    override val name: String = sender.name

    override fun sendMessage(message: Component) {
        SpigotPlatform.adventure?.sender(sender)?.sendMessage(message)
    }

    override fun sendMessage(message: String) {
        sender.sendMessage(message)
    }

    override fun sendTranslatedMessage(message: String) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message))
    }

}