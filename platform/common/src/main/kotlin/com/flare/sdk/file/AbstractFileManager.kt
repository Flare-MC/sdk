package com.flare.sdk.file

import com.flare.sdk.FlareException
import java.io.File
import java.nio.file.Path


/*
 * Project: sdk
 * Created at: 1/2/25 22:18
 * Created by: Dani-error
 */
abstract class AbstractFileManager(val base: Path) {

    val files: MutableMap<String, FlareFile> = mutableMapOf()

    fun register(name: String, vararg path: String): FlareFile {
        if (path.isEmpty()) throw FlareException("Path cannot be empty.")

        val finalPath = path.joinToString(separator = File.separator)
        val file = FlareFile(this, base, (if (finalPath.endsWith(".yml")) finalPath else "$finalPath.yml"))
        files[name.lowercase()] = file
        return file
    }

    fun register(name: String, path: String): FlareFile {
        val file = FlareFile(this, base, (if (path.endsWith(".yml")) path else "$path.yml"))
        files[name.lowercase()] = file
        return file
    }

    fun register(name: String): FlareFile {
        val file = FlareFile(this, base, (if (name.endsWith(".yml")) name else "$name.yml"))
        files[name.lowercase()] = file
        return file
    }

    fun get(name: String): FlareFile? =
        files[name.lowercase()]

    abstract fun getYAML(file: File): YAMLConfiguration
    abstract fun resourceExists(fileName: String): Boolean
    abstract fun saveResource(fileName: String)

}