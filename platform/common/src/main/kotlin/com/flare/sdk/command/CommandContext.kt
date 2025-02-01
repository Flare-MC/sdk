package com.flare.sdk.command

import com.flare.sdk.player.Player


/*
 * Project: sdk
 * Created at: 1/2/25 18:34
 * Created by: Dani-error
 */
class CommandContext(
    val sender: CommandSender,
    label: String,
    args: Array<String>,
    subCommand: Int
) {

    val label: String = buildString {
        append(label)
        args.take(subCommand).forEach { append(".$it") }
    }
    val args: List<String> = args.slice(subCommand until args.size)

    val length: Int get() = args.size
    val isPlayer: Boolean get() = sender is Player
    fun argument(index: Int): String = args[index]
    fun asPlayer(): Player? = sender as? Player

}