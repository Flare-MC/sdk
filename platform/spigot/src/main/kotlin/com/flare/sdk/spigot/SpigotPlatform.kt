@file:Suppress("unused")

package com.flare.sdk.spigot

import com.flare.sdk.file.AbstractFileManager
import com.flare.sdk.platform.PlatformEntryPoint
import com.flare.sdk.platform.PlatformType
import com.flare.sdk.spigot.command.CommandManager
import com.flare.sdk.spigot.file.FileManager
import com.flare.sdk.spigot.player.PlayerListener
import com.flare.sdk.spigot.player.PlayerManager
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin


/*
 * Project: sdk
 * Created at: 30/01/2025 20:30
 * Created by: Dani-error
 */
class SpigotPlatform(platform: JavaPlugin) : PlatformEntryPoint<JavaPlugin>(platform) {

    override val type: PlatformType = PlatformType.SPIGOT

    override val playerManager: PlayerManager = PlayerManager()
    override val commandManager: CommandManager = CommandManager(this)
    override val fileManager: FileManager = FileManager(platform)

    override fun setupAdventure() {
        adventure = BukkitAudiences.create(platform)
    }

    override fun disableAdventure() {
        if (adventure != null) {
            adventure?.close()
            adventure = null
        }
    }

    override fun setupEvents() {
        Bukkit.getPluginManager().registerEvents(PlayerListener(playerManager), platform)
    }

    companion object {

        var adventure: BukkitAudiences? = null

    }

}