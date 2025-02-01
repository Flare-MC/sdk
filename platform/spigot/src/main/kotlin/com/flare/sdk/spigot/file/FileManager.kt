package com.flare.sdk.spigot.file

import com.flare.sdk.file.AbstractFileManager
import com.flare.sdk.file.YAMLConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File


/*
 * Project: sdk
 * Created at: 1/2/25 22:39
 * Created by: Dani-error
 */
class FileManager(private val plugin: JavaPlugin) : AbstractFileManager(plugin.dataFolder.toPath()) {

    override fun getYAML(file: File): YAMLConfiguration =
        BukkitYAMLFile(file)

    override fun resourceExists(fileName: String): Boolean =
        plugin.getResource(fileName) != null

    override fun saveResource(fileName: String) =
        plugin.saveResource(fileName, false)

}