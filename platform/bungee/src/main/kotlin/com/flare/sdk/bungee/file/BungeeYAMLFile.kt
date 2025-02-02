package com.flare.sdk.bungee.file

import com.flare.sdk.file.ConfigurationSection
import com.flare.sdk.file.YAMLConfiguration
import net.md_5.bungee.config.Configuration
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

    private fun wrapSection(section: Configuration): ConfigurationSection {
        return object : ConfigurationSection {
            override fun contains(path: String): Boolean =
                section.contains(path)

            override fun getInteger(path: String): Int =
                section.getInt(path)

            override fun getDouble(path: String): Double =
                section.getDouble(path)

            override fun getString(path: String): String =
                section.getString(path) ?: ""

            override fun getBoolean(path: String): Boolean =
                section.getBoolean(path)

            override fun getLong(path: String): Long =
                section.getLong(path)

            override fun getStringList(path: String): List<String> =
                section.getStringList(path)

            override fun set(path: String, value: Any?) =
                section.set(path, value)

            override fun getKeys(): List<String> =
                section.keys.toList()

            override fun getSection(path: String): ConfigurationSection? {
                val otherSection = section.getSection(path) ?: return null

                return wrapSection(otherSection)
            }

        }
    }

    override fun getSection(path: String): ConfigurationSection? {
        val section = configuration.getSection(path) ?: return null

        return wrapSection(section)
    }

    override fun getKeys(): List<String> =
        configuration.keys.toList()

}