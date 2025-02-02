package com.flare.sdk.file


/*
 * Project: sdk
 * Created at: 2/2/25 10:19
 * Created by: Dani-error
 */
interface ConfigurationSection {

    fun contains(path: String): Boolean

    fun getInteger(path: String): Int
    fun getDouble(path: String): Double
    fun getString(path: String): String
    fun getBoolean(path: String): Boolean
    fun getLong(path: String): Long
    fun getStringList(path: String): List<String>
    fun set(path: String, value: Any?)
    fun getSection(path: String): ConfigurationSection?
    fun getKeys(): List<String>

}