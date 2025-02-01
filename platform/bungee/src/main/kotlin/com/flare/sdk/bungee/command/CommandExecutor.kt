package com.flare.sdk.bungee.command

import com.flare.sdk.command.CommandContext
import com.flare.sdk.command.CommandDefinition
import com.flare.sdk.command.CommandInfo
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor
import java.lang.reflect.Method
import java.util.logging.Level


/*
 * Project: sdk
 * Created at: 1/2/25 20:06
 * Created by: Dani-error
 */
class CommandExecutor(private val name: String, private val commandManager: CommandManager, private val definition: CommandDefinition) : Command(definition.commandInfo.name), TabExecutor {

    override fun execute(sender: CommandSender, args: Array<String>) {
        fun runDefault() {
            val method: Method = definition.executeMethod
            val classInstance: Any = definition.classInstance

            val commandInfo: CommandInfo = definition.commandInfo

            if (commandInfo.permission.isNotEmpty() && (!sender.hasPermission(commandInfo.permission))) {
                sender.sendMessage(
                    *TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes(
                        '&',
                        commandManager.localeHandler.noPermissions
                    ))
                )
                return
            }

            if (commandInfo.inGameOnly && sender !is ProxiedPlayer) {
                sender.sendMessage(
                    *TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes(
                        '&',
                        commandManager.localeHandler.executedByConsole
                    ))
                )
                return
            }

            try {
                method.invoke(classInstance,
                    CommandContext(
                        commandManager.bindSender(sender),
                        name,
                        args,
                        0
                    )
                )
            } catch (e: Throwable) {
                commandManager.platform.logger.log(Level.WARNING, "Couldn't handle command execution", e)
            }
        }

        fun findSubCommand(commandList: List<com.flare.sdk.command.Command>, alias: String): com.flare.sdk.command.Command? {
            return commandList.find {
                it.commandInfo.name.equals(alias, ignoreCase = true) ||
                        it.commandInfo.aliases.any { other -> other.equals(alias, ignoreCase = true) }
            }
        }

        fun executeSubCommand(currentCommand: com.flare.sdk.command.Command, currentIndex: Int) {
            if (currentIndex >= args.size) {
                currentCommand.execute(
                    CommandContext(
                        commandManager.bindSender(sender),
                        name,
                        args,
                        currentIndex
                    )
                )
                return
            }

            val nextSubCommand = findSubCommand(currentCommand.commandInfo.subCommands, args[currentIndex])
            if (nextSubCommand != null) {
                executeSubCommand(nextSubCommand, currentIndex + 1)
            } else {
                currentCommand.execute(
                    CommandContext(
                        commandManager.bindSender(sender),
                        name,
                        args,
                        currentIndex
                    )
                )
            }
        }

        if (args.isEmpty()) {
            runDefault()
        } else {
            val subCommand = findSubCommand(definition.commandInfo.subCommands, args[0])
            if (subCommand != null) {
                executeSubCommand(subCommand, 1)
            } else {
                runDefault()
            }
        }
    }

    override fun onTabComplete(sender: CommandSender, args: Array<String>): MutableList<String> {
        fun findSubCommand(commandList: List<com.flare.sdk.command.Command>, alias: String): com.flare.sdk.command.Command? {
            return commandList.find {
                it.commandInfo.name.equals(alias, ignoreCase = true) ||
                        it.commandInfo.aliases.any { other -> other.equals(alias, ignoreCase = true) }
            }
        }

        fun getTabCompletion(currentCommand: com.flare.sdk.command.Command, currentIndex: Int): List<String> {
            if (currentIndex >= args.size) {
                return currentCommand.suggest(
                    CommandContext(
                        commandManager.bindSender(sender),
                        name,
                        args,
                        currentIndex
                    )
                )
            }

            val nextSubCommand = findSubCommand(currentCommand.commandInfo.subCommands, args[currentIndex])
            return nextSubCommand?.let { getTabCompletion(it, currentIndex + 1) } ?: currentCommand.suggest(
                CommandContext(
                    commandManager.bindSender(sender),
                    name,
                    args,
                    currentIndex
                )
            )
        }

        @Suppress("UNCHECKED_CAST")
        fun getDefault(): MutableList<String> {
            return definition.tabCompleteMethod.invoke(definition.classInstance,
                CommandContext(
                    commandManager.bindSender(sender),
                    name,
                    args,
                    0
                )
            ) as MutableList<String>
        }

        if (args.isEmpty()) {
            return getDefault()
        }

        val subCommand = findSubCommand(definition.commandInfo.subCommands, args[0])
        return if (subCommand != null) {
            getTabCompletion(subCommand, 1).toMutableList()
        } else {
            getDefault()
        }
    }

}