package com.flare.sdk.spigot.file

import com.flare.sdk.file.ConfigurationSection
import com.flare.sdk.file.YAMLConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File


/*
 * Project: sdk
 * Created at: 1/2/25 22:41
 * Created by: Dani-error
 */
class BukkitYAMLFile(private val file: File) : YAMLConfiguration(file) {

    private val configuration = YamlConfiguration.loadConfiguration(file)

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
        configuration.save(file)

    override fun set(path: String, value: Any?) =
        configuration.set(path, value)

    private fun wrapSection(section: org.bukkit.configuration.ConfigurationSection): ConfigurationSection {
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

            override fun getSection(path: String): ConfigurationSection? {
                val otherSection = section.getConfigurationSection(path) ?: return null

                return wrapSection(otherSection)
            }

            override fun getKeys(): List<String> =
                section.getKeys(false).toList()

        }
    }

    override fun getSection(path: String): ConfigurationSection? {
        val section = configuration.getConfigurationSection(path) ?: return null

        return wrapSection(section)
    }

    override fun getKeys(): List<String> =
        configuration.getKeys(false).toList()

}