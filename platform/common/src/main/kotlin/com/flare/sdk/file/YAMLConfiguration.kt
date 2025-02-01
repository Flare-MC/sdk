package com.flare.sdk.file

import java.io.File


/*
 * Project: sdk
 * Created at: 1/2/25 22:28
 * Created by: Dani-error
 */
abstract class YAMLConfiguration(file: File) {

    abstract fun contains(path: String): Boolean

    abstract fun getInteger(path: String): Int
    abstract fun getDouble(path: String): Double
    abstract fun getString(path: String): String
    abstract fun getBoolean(path: String): Boolean
    abstract fun getLong(path: String): Long
    abstract fun getStringList(path: String): List<String>

    abstract fun save()
    abstract fun set(path: String, value: Any?)

}