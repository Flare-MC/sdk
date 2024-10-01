package com.flare.sdk

import com.flare.sdk.annotations.DataFolder
import com.flare.sdk.annotations.PluginConfiguration
import com.flare.sdk.platform.Platform
import com.flare.sdk.platform.PlatformType
import com.flare.sdk.util.ReflectionUtil
import java.io.File
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.nio.file.Path


/*
 * Project: sdk
 * Created at: 30/9/24 15:25
 * Created by: Dani-error
 */
@Suppress("unused")
class Flare(private val platform: Platform, entryPoint: Class<*>, private val configuration: FlareConfiguration) {

    private val entryPointInstance: Any

    private val loadMethod: Method?
    private val enableMethod: Method?
    private val disableMethod: Method?

    init {
        if (instance == null) {
            instance = this
            try {
                entryPointInstance = entryPoint.getConstructor().newInstance()
            } catch (e: Exception) {
                throw FlareException("An exception occurred while creating an entry point instance. ${e.message}")
            }

            loadMethod = ReflectionUtil.getMethod(entryPoint, "onLoad")
            enableMethod = ReflectionUtil.getMethod(entryPoint, "onEnable")
            disableMethod = ReflectionUtil.getMethod(entryPoint, "onDisable")

            entryPoint.getDeclaredFields().forEach {
                if (Modifier.isFinal(it.modifiers)) return@forEach

                if (it.isAnnotationPresent(com.flare.sdk.annotations.PlatformAccessor::class.java)) {
                    it.isAccessible = true

                    if (it.type.isAssignableFrom(Platform::class.java)) {
                        it.set(entryPointInstance, platform)
                    } else if (it.type.isAssignableFrom(PlatformType::class.java)) {
                        it.set(entryPointInstance, platform.platformType)
                    } else throw FlareException("Fields annotated with @Platform should be Platform or PlatformType.")
                } else if (it.isAnnotationPresent(DataFolder::class.java)) {
                    it.isAccessible = true

                    if (it.type.isAssignableFrom(File::class.java)) {
                        it.set(entryPointInstance, platform.dataDirectory.toFile())
                    } else if (it.type.isAssignableFrom(Path::class.java)) {
                        it.set(entryPointInstance, platform.dataDirectory)
                    } else throw FlareException("Fields annotated with @DataFolder should be Path or File.")
                } else if (it.isAnnotationPresent(PluginConfiguration::class.java)) {
                    it.isAccessible = true

                    if (it.type.isAssignableFrom(FlareConfiguration::class.java)) {
                        it.set(entryPointInstance, configuration)
                    } else throw FlareException("Fields annotated with @PluginConfiguration should be FlareConfiguration.")
                }
            }
        } else throw FlareException("Cannot be two instances of Flare.")
    }

    fun onLoad() {
        try {
            loadMethod?.invoke(entryPointInstance)
        } catch (e: Exception) {
            throw FlareException("An exception occurred while invoking load method. ${e.message}")
        }
    }

    fun onEnable() {
        try {
            enableMethod?.invoke(entryPointInstance)
        } catch (e: Exception) {
            throw FlareException("An exception occurred while invoking enable method. ${e.message}")
        }
    }

    fun onDisable() {
        try {
            disableMethod?.invoke(entryPointInstance)
        } catch (e: Exception) {
            throw FlareException("An exception occurred while invoking disable method. ${e.message}")
        }
    }

    companion object {

        var instance: Flare? = null

    }
}