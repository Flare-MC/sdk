package com.flare.sdk.command


/*
 * Project: sdk
 * Created at: 1/2/25 18:32
 * Created by: Dani-error
 */
data class CommandInfo(
    val name: String,
    val permission: String = "",
    val aliases: List<String> = emptyList(),
    val description: String = "",
    val usage: String = "",
    val inGameOnly: Boolean = true,
    val subCommands: List<Command> = emptyList()
) {
    companion object {
        fun create(name: String) = CommandInfoBuilder(name)
    }

    class CommandInfoBuilder(private val name: String) {
        private var permission: String = ""
        private var aliases: MutableList<String> = mutableListOf()
        private var description: String = ""
        private var usage: String = ""
        private var inGameOnly: Boolean = true
        private var subCommands: MutableList<Command> = mutableListOf()

        fun permission(permission: String) = apply { this.permission = permission }
        fun aliases(vararg aliases: String) = apply { this.aliases = aliases.toMutableList() }
        fun aliases(aliases: List<String>) = apply { this.aliases = aliases.toMutableList() }
        fun subCommands(vararg subCommands: Command) = apply { this.subCommands = subCommands.toMutableList() }
        fun subCommands(subCommands: List<Command>) = apply { this.subCommands = subCommands.toMutableList() }
        fun description(description: String) = apply { this.description = description }
        fun usage(usage: String) = apply { this.usage = usage }
        fun inGameOnly(inGameOnly: Boolean) = apply { this.inGameOnly = inGameOnly }

        fun build() = CommandInfo(name, permission, aliases, description, usage, inGameOnly, subCommands)
    }
}