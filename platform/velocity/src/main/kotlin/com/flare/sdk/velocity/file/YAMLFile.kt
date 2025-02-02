package com.flare.sdk.velocity.file

import com.flare.sdk.FlareException
import com.flare.sdk.file.ConfigurationSection
import com.flare.sdk.file.YAMLConfiguration
import org.bspfsystems.yamlconfiguration.file.YamlConfiguration
import java.io.File
import java.io.IOException


/*
 * Project: sdk
 * Created at: 1/2/25 22:45
 * Created by: Dani-error
 */
class YAMLFile(private val file: File) : YAMLConfiguration(file) {

    private val configuration = YamlConfiguration()

    init {
        try {
            configuration.load(file)
        } catch (e: Exception) {
            throw FlareException("Couldn't load YAML file.", e)
        }
    }

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

    private fun wrapSection(section: org.bspfsystems.yamlconfiguration.configuration.ConfigurationSection): ConfigurationSection {
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

    override fun save() {
        try {
            configuration.save(file)
        } catch (e: IOException) {
            throw FlareException("Couldn't save YAML file.", e)
        }
    }

    override fun set(path: String, value: Any?) =
        configuration.set(path, value)

    override fun getKeys(): List<String> =
        configuration.getKeys(false).toList()
}