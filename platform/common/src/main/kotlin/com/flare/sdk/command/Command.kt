package com.flare.sdk.command


/*
 * Project: sdk
 * Created at: 1/2/25 18:32
 * Created by: Dani-error
 */
abstract class Command(val commandInfo: CommandInfo) {

    constructor(name: String) : this(CommandInfo.create(name).build())

    constructor(name: String, inGameOnly: Boolean) : this(CommandInfo.create(name).inGameOnly(inGameOnly).build())

    constructor(name: String, permission: String) : this(CommandInfo.create(name).permission(permission).build())


    constructor(name: String, permission: String, inGameOnly: Boolean) : this(CommandInfo.create(name).permission(permission).inGameOnly(inGameOnly).build())

    constructor(name: String, permission: String, vararg aliases: String) : this(CommandInfo.create(name).permission(permission).aliases(aliases.toList()).build())

    constructor(name: String, permission: String, aliases: List<String>) : this(CommandInfo.create(name).permission(permission).aliases(aliases).build())

    constructor(name: String, permission: String, inGameOnly: Boolean, vararg aliases: String) : this(
        CommandInfo.create(name).permission(permission).inGameOnly(inGameOnly).aliases(aliases.toList())
            .build()
    )

    constructor(name: String, permission: String, inGameOnly: Boolean, aliases: List<String>) : this(CommandInfo.create(name).permission(permission).inGameOnly(inGameOnly).aliases(aliases).build())

    constructor(name: String, inGameOnly: Boolean, vararg aliases: String) : this(CommandInfo.create(name).inGameOnly(inGameOnly).aliases(aliases.toList()).build())

    constructor(name: String, inGameOnly: Boolean, aliases: List<String>) : this(CommandInfo.create(name).inGameOnly(inGameOnly).aliases(aliases.toList()).build())

    abstract fun execute(context: CommandContext)

    open fun suggest(context: CommandContext): List<String> = emptyList<String>().toMutableList()
}