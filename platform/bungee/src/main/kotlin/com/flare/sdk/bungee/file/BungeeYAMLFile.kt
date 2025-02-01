package com.flare.sdk.bungee.file

import com.flare.sdk.file.YAMLConfiguration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.File

/*
 * Project: sdk
 * Created at: 1/2/25 22:53
 * Created by: Dani-error
 */
class BungeeYAMLFile(private val file: File) : YAMLConfiguration(file) {

    private val provider = ConfigurationProvider.getProvider(YamlConfiguration::class.java)
    private val configuration =
        provider.load(file)

    override fun contains(path: String): Boolean =
        configuration.contains(path)

    override fun getInteger(path: String): Int =
        configuration.getInt(path)

    override fun getDouble(path: String): Double =
        configuration.getDouble(path)

    override fun getString(path: String): String =
        configuration.getString(path) ?: ""

    override fun getBoolean(path: String): Boolean =
        configuration.getBoolean(path)

    override fun getLong(path: String): Long =
        configuration.getLong(path)

    override fun getStringList(path: String): List<String> =
        configuration.getStringList(path)

    override fun save() =
        provider.save(configuration, file)

    override fun set(path: String, value: Any?) =
        configuration.set(path, value)

}