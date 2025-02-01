package com.flare.sdk.file

import com.flare.sdk.FlareException
import java.io.File
import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths


/*
 * Project: sdk
 * Created at: 1/2/25 22:20
 * Created by: Dani-error
 */
class FlareFile(private val fileManager: AbstractFileManager, base: Path, fileName: String) {

    val file: File = File(Paths.get(base.toString(), fileName).toUri())
    private var configuration: YAMLConfiguration

    init {
        if (!file.exists()) {
            file.getParentFile().mkdirs()
            if (!fileManager.resourceExists(fileName)) {
                try {
                    file.createNewFile()
                } catch (e: IOException) {
                    throw FlareException("Failed to create new file $fileName")
                }
            } else {
                fileManager.saveResource(fileName)
            }
        }

        configuration = fileManager.getYAML(file)
    }

    fun getDouble(path: String): Double {
        return if (configuration.contains(path)) {
            configuration.getDouble(path)
        } else 0.0
    }

    fun getInt(path: String): Int {
        return if (configuration.contains(path)) {
            configuration.getInteger(path)
        } else 0
    }

    fun getBoolean(path: String): Boolean {
        return if (configuration.contains(path)) {
            configuration.getBoolean(path)
        } else false
    }

    fun getLong(path: String): Long {
        return if (configuration.contains(path)) {
            configuration.getLong(path)
        } else 0L
    }

    fun getString(path: String): String? {
        return if (configuration.contains(path)) {
            configuration.getString(path)
        } else null
    }

    fun getStringList(path: String): List<String> {
        if (configuration.contains(path)) {
            return configuration.getStringList(path)
        }

        return emptyList()
    }

    fun set(path: String, value: Any?) =
        configuration.set(path, value)

    fun contains(path: String): Boolean =
        configuration.contains(path)

    fun save() {
        try {
            configuration.save()
        } catch (e: IOException) {
            throw FlareException("Could not save config file $file", e)
        }
    }

    fun reload() {
        configuration = fileManager.getYAML(file)
    }

}