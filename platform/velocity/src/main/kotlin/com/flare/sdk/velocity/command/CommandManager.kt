package com.flare.sdk.velocity.command

import com.flare.sdk.command.*
import com.flare.sdk.velocity.VelocityPlatform
import com.velocitypowered.api.command.CommandMeta
import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.command.SimpleCommand
import com.velocitypowered.api.proxy.Player
import java.lang.reflect.Method


/*
 * Project: sdk
 * Created at: 1/2/25 19:50
 * Created by: Dani-error
 */
class CommandManager(private val _platform: VelocityPlatform) : AbstractCommandManager<Any, CommandSource>(_platform.type, _platform.platform) {

    override val consoleSender = ConsoleCommandSender(_platform.proxyServer)

    override fun registerCommand(
        commandInfo: CommandInfo,
        name: String,
        executeMethod: Method,
        tabCompleteMethod: Method,
        classInstance: Command
    ) {
        if (name.isEmpty() || name.replace(" ", "").isEmpty()) return

        val definition = CommandDefinition(executeMethod, tabCompleteMethod, classInstance, commandInfo)

        val commandManager = _platform.proxyServer.commandManager

        val commandMeta: CommandMeta =
            commandManager.metaBuilder(name)
                .plugin(this)
                .build()

        val commandToRegister: SimpleCommand = CommandExecutor(name, this, definition)

        commandManager.register(commandMeta, commandToRegister)
    }

    override fun bindSender(sender: CommandSource): CommandSender {
        return when (sender) {
            is Player -> {
                _platform.playerManager.getOrCreatePlayer(sender)
            }

            else -> consoleSender
        }
    }

}