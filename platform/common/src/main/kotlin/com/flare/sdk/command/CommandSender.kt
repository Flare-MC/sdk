package com.flare.sdk.command

import net.kyori.adventure.text.Component


/*
 * Project: sdk
 * Created at: 1/2/25 18:35
 * Created by: Dani-error
 */
interface CommandSender {

    val name: String

    fun sendMessage(message: Component)
    fun sendMessage(message: String)
    fun sendTranslatedMessage(message: String)

}