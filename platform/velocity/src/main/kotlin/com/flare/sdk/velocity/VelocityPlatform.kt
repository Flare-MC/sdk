@file:Suppress("unused")

package com.flare.sdk.velocity

import com.flare.sdk.FlareException
import com.flare.sdk.file.AbstractFileManager
import com.flare.sdk.platform.Platform
import com.flare.sdk.platform.PlatformEntryPoint
import com.flare.sdk.platform.PlatformType
import com.flare.sdk.task.AbstractTaskManager
import com.flare.sdk.velocity.command.CommandManager
import com.flare.sdk.velocity.file.FileManager
import com.flare.sdk.velocity.player.PlayerListener
import com.flare.sdk.velocity.player.PlayerManager
import com.flare.sdk.velocity.task.TaskManager
import com.velocitypowered.api.proxy.ProxyServer

/*
 * Project: sdk
 * Created at: 30/01/2025 20:30
 * Created by: Dani-error
 */
class VelocityPlatform(platform: Any) : PlatformEntryPoint<Any>(platform) {

    val proxyServer: ProxyServer

    override val type: PlatformType = PlatformType.VELOCITY

    override val playerManager: PlayerManager = PlayerManager()
    override val commandManager: CommandManager
    override val fileManager: AbstractFileManager = FileManager(platform as Platform)
    override val taskManager: AbstractTaskManager

    init {
        val serverField = platform.javaClass.getDeclaredField("server")
        serverField.isAccessible = true
        try {
            proxyServer = serverField.get(platform) as ProxyServer
            taskManager = TaskManager(platform, proxyServer)
            commandManager = CommandManager(this)
        } catch (e: Exception) {
            throw FlareException("Couldn't setup the event conversion system... ${e.message}")
        }

    }

    override fun setupEvents() {
        proxyServer.eventManager.register(platform, PlayerListener(playerManager))
    }

}