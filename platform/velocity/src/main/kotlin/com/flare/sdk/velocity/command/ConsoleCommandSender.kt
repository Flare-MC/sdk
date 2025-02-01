package com.flare.sdk.velocity.command

import com.flare.sdk.command.CommandSender
import com.velocitypowered.api.proxy.ProxyServer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer


/*
 * Project: sdk
 * Created at: 1/2/25 19:39
 * Created by: Dani-error
 */
class ConsoleCommandSender(private val proxyServer: ProxyServer) : CommandSender {

    override val name: String = "Console"

    override fun sendMessage(message: Component) = proxyServer.consoleCommandSource.sendMessage(message)

    override fun sendMessage(message: String) {
        proxyServer.consoleCommandSource.sendPlainMessage(message)
    }

    override fun sendTranslatedMessage(message: String) {
        proxyServer.consoleCommandSource.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message))
    }
}