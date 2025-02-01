package com.flare.sdk.bungee.command

import com.flare.sdk.bungee.BungeePlatform
import com.flare.sdk.command.AbstractCommandManager
import com.flare.sdk.command.Command
import com.flare.sdk.command.CommandDefinition
import com.flare.sdk.command.CommandInfo
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Plugin
import java.lang.reflect.Method


/*
 * Project: sdk
 * Created at: 1/2/25 19:50
 * Created by: Dani-error
 */
class CommandManager(private val _platform: BungeePlatform) : AbstractCommandManager<Plugin, CommandSender>(_platform.type, _platform.platform) {

    override val consoleSender = CommandSenderWrapper(platform.proxy.console)

    override fun registerCommand(
        commandInfo: CommandInfo,
        name: String,
        executeMethod: Method,
        tabCompleteMethod: Method,
        classInstance: Command
    ) {
        if (name.isEmpty() || name.replace(" ", "").isEmpty()) return

        val definition = CommandDefinition(executeMethod, tabCompleteMethod, classInstance, commandInfo)

        platform.proxy.pluginManager.registerCommand(platform, CommandExecutor(name, this, definition))
    }

    override fun bindSender(sender: CommandSender): com.flare.sdk.command.CommandSender {
        return when (sender) {
            is ProxiedPlayer -> {
                _platform.playerManager.getOrCreatePlayer(sender)
            }

            platform.proxy.console -> {
                consoleSender
            }

            else -> {
                CommandSenderWrapper(sender)
            }
        }
    }

}