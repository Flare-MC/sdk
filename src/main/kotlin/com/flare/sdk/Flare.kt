package com.flare.sdk

import com.flare.sdk.annotations.DataFolder
import com.flare.sdk.annotations.PluginConfiguration
import com.flare.sdk.platform.Platform
import com.flare.sdk.platform.PlatformEntryPoint
import com.flare.sdk.platform.PlatformType
import com.flare.sdk.player.AbstractPlayerManager
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
class Flare(private val platform: Platform, private val configuration: FlareConfiguration) {

    private val entryPointInstance: Any

    private val loadMethod: Method?
    private val enableMethod: Method?
    private val disableMethod: Method?

    private val platformEntryPoint: PlatformEntryPoint<*>?
    lateinit var playerManager: AbstractPlayerManager<*>

    init {
        _instance = this

        val entryPoint = configuration.entrypoint
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

        val platformEntry = platform.platformType.entrypoint

        val instance: PlatformEntryPoint<*>?
        if (platformEntry.interfaces.any { it == PlatformEntryPoint::class.java }) {
            try {
                instance = platformEntry.getConstructor().newInstance() as PlatformEntryPoint<*>

                playerManager = instance.playerManager
            } catch (e: Exception) {
                throw FlareException("An exception occurred while creating a platform entry point instance. ${e.message}")
            }
        } else instance = null
        platformEntryPoint = instance
    }

    fun onLoad() {
        if (platformEntryPoint == null) return

        try {
            loadMethod?.invoke(entryPointInstance)
        } catch (e: Exception) {
            throw FlareException("An exception occurred while invoking load method. ${e.message}")
        }
    }

    fun onEnable() {
        if (platformEntryPoint == null) return

        platformEntryPoint.setupEvents(platform)
        try {
            enableMethod?.invoke(entryPointInstance)
        } catch (e: Exception) {
            throw FlareException("An exception occurred while invoking enable method. ${e.message}")
        }
    }

    fun onDisable() {
        if (platformEntryPoint == null) return

        try {
            disableMethod?.invoke(entryPointInstance)
        } catch (e: Exception) {
            throw FlareException("An exception occurred while invoking disable method. ${e.message}")
        }
    }

    companion object {
        private var _instance: Flare? = null

        val instance: Flare
            get() = _instance ?: throw IllegalStateException("Flare has not been initialized yet.")
    }

}

@Suppress("UNCHECKED_CAST")
private fun <T> PlatformEntryPoint<T>.setupEvents(plugin: Any) {
    this.setupEvents(plugin as T)
}
