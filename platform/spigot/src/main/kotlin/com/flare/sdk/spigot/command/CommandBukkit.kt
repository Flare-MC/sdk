package com.flare.sdk.spigot.command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin
import java.util.logging.Level


/*
 * Project: sdk
 * Created at: 1/2/25 19:12
 * Created by: Dani-error
 */
class CommandBukkit(
    name: String,
    private val executor: CommandExecutor,
    private val ownerPlugin: Plugin
) : Command(name) {

    init {
        usageMessage = ""
    }

    override fun execute(sender: CommandSender, label: String, args: Array<String>): Boolean {
        if (!ownerPlugin.isEnabled) return false

        if (!testPermission(sender)) return true

        try {
            executor.onCommand(sender, this, label, args)
            return true
        } catch (e: Throwable) {
            ownerPlugin.logger.log(
                Level.SEVERE, "Unhandled exception executing command '" + label + "' in plugin "
                        + ownerPlugin.description.fullName, e
            )
            return false
        }
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<String>): MutableList<String> {
        val tab: MutableList<String> =
            executor.onTabComplete(sender, this, alias, args)
        return tab
    }
}