package com.flare.sdk.spigot.command

import com.flare.sdk.FlareException
import com.flare.sdk.command.CommandContext
import com.flare.sdk.command.CommandDefinition
import com.flare.sdk.command.CommandInfo
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.lang.reflect.Method
import java.util.*
import java.util.logging.Level


/*
 * Project: sdk
 * Created at: 1/2/25 19:13
 * Created by: Dani-error
 */
class CommandExecutor(private val commandManager: CommandManager) : CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        for (i in args.size downTo 0) {
            val buffer = StringBuilder()
            buffer.append(label.lowercase(Locale.getDefault()))
            for (x in 0 until i) {
                buffer.append(".").append(args[x].lowercase(Locale.getDefault()))
            }

            val cmdLabel = buffer.toString()
            if (commandManager.commandMap.containsKey(cmdLabel)) {
                val definition: CommandDefinition = commandManager.commandMap[cmdLabel] ?: continue

                val method: Method = definition.executeMethod
                val classInstance: Any = definition.classInstance

                val commandInfo: CommandInfo = definition.commandInfo

                if (commandInfo.permission.isNotEmpty() && (!sender.hasPermission(commandInfo.permission))) {
                    sender.sendMessage(
                        ChatColor.translateAlternateColorCodes(
                            '&',
                            commandManager.localeHandler.noPermissions
                        )
                    )
                    return true
                }

                if (commandInfo.inGameOnly && sender !is Player) {
                    sender.sendMessage(
                        ChatColor.translateAlternateColorCodes(
                            '&',
                            commandManager.localeHandler.executedByConsole
                        )
                    )
                    return true
                }

                try {
                    method.invoke(classInstance,
                        CommandContext(
                            commandManager.bindSender(sender),
                            label,
                            args,
                            cmdLabel.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size - 1
                        )
                    )
                } catch (e: Throwable) {
                    commandManager.platform.logger.log(Level.WARNING, "Couldn't handle command execution", e)
                }
                return true
            }
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<String>
    ): MutableList<String> {
        for (i in args.size downTo 0) {
            val buffer = java.lang.StringBuilder()
            buffer.append(label.lowercase(Locale.getDefault()))
            for (x in 0 until i) {
                buffer.append(".").append(args[x].lowercase(Locale.getDefault()))
            }

            val cmdLabel = buffer.toString()
            if (commandManager.commandMap.containsKey(cmdLabel)) {
                val definition: CommandDefinition = commandManager.commandMap[cmdLabel] ?: continue
                val method: Method = definition.tabCompleteMethod

                val classInstance: Any = definition.classInstance

                try {
                    if (method.returnType != MutableList::class.java) {
                        return emptyList<String>().toMutableList()
                    }

                    val result = method.invoke(classInstance,
                        CommandContext(
                            commandManager.bindSender(sender),
                            label,
                            args,
                            cmdLabel.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size - 1
                        )
                    )

                    if (result is MutableList<*>) return result.map { it.toString() }.toMutableList() else throw FlareException("The tab complete method doesn't return a List<String> object!")
                } catch (e: Throwable) {
                    commandManager.platform.logger.log(Level.WARNING, "Couldn't handle command tab completion", e)
                }
            }
        }

        return mutableListOf()
    }
}