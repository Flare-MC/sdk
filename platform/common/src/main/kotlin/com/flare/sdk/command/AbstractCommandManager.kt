package com.flare.sdk.command

import com.flare.sdk.FlareException
import com.flare.sdk.platform.PlatformType
import java.lang.reflect.Method
import java.util.function.Predicate


/*
 * Project: sdk
 * Created at: 1/2/25 18:38
 * Created by: Dani-error
 */
abstract class AbstractCommandManager<T, S>(private val platformType: PlatformType, val platform: T) {

    open var localeHandler = object : CommandLocaleHandler { }
    private val disabledCommands: MutableList<Predicate<Command>> = mutableListOf()

    abstract val consoleSender: CommandSender

    fun registerCommand(command: Command) {
        val commandInfo: CommandInfo = command.commandInfo

        if (isDisabled(command)) return

        for (subCommand in commandInfo.subCommands) {
            registerSubcommand(subCommand, listOf(command.commandInfo.name))

            for (alias in commandInfo.aliases) {
                registerSubcommand(subCommand, listOf(alias))
            }
        }

        try {
            registerCommand(
                commandInfo,
                command.javaClass.getMethod("execute", CommandContext::class.java),
                command.javaClass.getMethod(
                    "suggest",
                    CommandContext::class.java
                ),
                command
            )
        } catch (e: NoSuchMethodException) {
            throw FlareException("Couldn't register single command, it doesn't exists the required methods?")
        }
    }

    fun isDisabled(command: Command): Boolean {
        for (condition in disabledCommands) {
            if (condition.test(command)) return true
        }

        return false
    }

    fun disableIf(predicate: Predicate<Command>) = apply { disabledCommands.add(predicate) }

    fun registerCommands(vararg commands: Command) {
        for (command in commands) {
            registerCommand(command)
        }
    }

    private fun registerCommand(
        commandInfo: CommandInfo,
        executeMethod: Method,
        tabCompleteMethod: Method,
        classInstance: Command
    ) {
        registerCommand(commandInfo, commandInfo.name, executeMethod, tabCompleteMethod, classInstance)

        for (alias in commandInfo.aliases) {
            registerCommand(commandInfo, alias, executeMethod, tabCompleteMethod, classInstance)
        }
    }

    private fun registerSubcommand(subCommand: Command, parent: List<String>) {
        if (isDisabled(subCommand) || platformType == PlatformType.BUNGEECORD|| platformType == PlatformType.VELOCITY) return

        val parentName = java.lang.String.join(".", parent)

        val names: MutableSet<String> = mutableSetOf(subCommand.commandInfo.name)
        names.addAll(subCommand.commandInfo.aliases)
        for (loopName in names) {
            val parents: MutableList<String> = ArrayList(parent)
            val name = if (parentName.isEmpty()) loopName else "$parentName.$loopName"

            try {
                registerCommand(
                    subCommand.commandInfo, name, subCommand.javaClass.getMethod(
                        "execute",
                        CommandContext::class.java
                    ), subCommand.javaClass.getMethod("suggest", CommandContext::class.java), subCommand
                )
            } catch (e: NoSuchMethodException) {
                throw FlareException("Couldn't register single command, it doesn't exists the required methods?")
            }

            parents.add(loopName)
            for (sub in subCommand.commandInfo.subCommands) {
                registerSubcommand(sub, parents)
            }
        }
    }

    protected abstract fun registerCommand(
        commandInfo: CommandInfo,
        name: String,
        executeMethod: Method,
        tabCompleteMethod: Method,
        classInstance: Command
    )

    abstract fun bindSender(sender: S): CommandSender

}