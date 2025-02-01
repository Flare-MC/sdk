package com.flare.sdk.spigot.command

import com.flare.sdk.FlareException
import com.flare.sdk.command.AbstractCommandManager
import com.flare.sdk.command.Command
import com.flare.sdk.command.CommandDefinition
import com.flare.sdk.command.CommandInfo
import com.flare.sdk.spigot.SpigotPlatform
import org.bukkit.Bukkit
import org.bukkit.command.CommandMap
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.SimplePluginManager
import org.bukkit.plugin.java.JavaPlugin
import java.lang.reflect.Method
import java.util.*


/*
 * Project: sdk
 * Created at: 1/2/25 18:51
 * Created by: Dani-error
 */
class CommandManager(private val _platform: SpigotPlatform) : AbstractCommandManager<JavaPlugin, CommandSender>(_platform.type, _platform.platform) {

    override val consoleSender = CommandSenderWrapper(Bukkit.getConsoleSender())
    private val executor: CommandExecutor = CommandExecutor(this)
    private val map: CommandMap
    val commandMap: MutableMap<String, CommandDefinition> = mutableMapOf()

    init {
        if (platform.server.pluginManager is SimplePluginManager) {
            val manager = platform.server.pluginManager as SimplePluginManager

            try {
                val field = SimplePluginManager::class.java.getDeclaredField("commandMap")
                field.isAccessible = true
                map = field[manager] as CommandMap
            } catch (e: Exception) {
                throw FlareException("Couldn't fetch the 'commandMap' field from the Plugin Manager")
            }
        } else {
            throw FlareException("Couldn't initialize the commands due to server's Plugin Manager is not an instance of SimplePluginManager.")
        }
    }

    override fun registerCommand(
        commandInfo: CommandInfo,
        name: String,
        executeMethod: Method,
        tabCompleteMethod: Method,
        classInstance: Command
    ) {
        if (name.isEmpty() || name.replace(" ", "").isEmpty()) return

        val definition = CommandDefinition(executeMethod, tabCompleteMethod, classInstance, commandInfo)

        commandMap[name.lowercase(Locale.getDefault())] = definition
        commandMap[platform.name + ':' + name.lowercase(Locale.getDefault())] = definition

        val cmdLabel =
            name.replace(".", ",").split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0].lowercase(
                Locale.getDefault()
            )

        if (map.getCommand(cmdLabel) == null) {
            map.register(platform.name, CommandBukkit(cmdLabel, executor, platform))
        }

        if (!commandInfo.description.trim().equals("", ignoreCase = true) && cmdLabel == name) {
            map.getCommand(cmdLabel)?.setDescription(commandInfo.description)
        }

        if (!commandInfo.usage.trim().equals("", ignoreCase = true) && cmdLabel == name) {
            map.getCommand(cmdLabel)?.setUsage(commandInfo.usage)
        }
    }

    override fun bindSender(sender: CommandSender): com.flare.sdk.command.CommandSender {
        return when (sender) {
            is Player -> {
                _platform.playerManager.getOrCreatePlayer(sender)
            }

            is ConsoleCommandSender -> {
                consoleSender
            }

            else -> {
                CommandSenderWrapper(sender)
            }
        }
    }

}