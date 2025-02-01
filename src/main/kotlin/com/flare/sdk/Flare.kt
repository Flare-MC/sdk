package com.flare.sdk

import com.flare.sdk.annotations.*
import com.flare.sdk.command.AbstractCommandManager
import com.flare.sdk.command.CommandSender
import com.flare.sdk.event.impl.PluginDisableEvent
import com.flare.sdk.event.impl.PluginEnableEvent
import com.flare.sdk.event.impl.PluginLoadEvent
import com.flare.sdk.file.AbstractFileManager
import com.flare.sdk.platform.Platform
import com.flare.sdk.platform.PlatformEntryPoint
import com.flare.sdk.platform.PlatformType
import com.flare.sdk.player.AbstractPlayerManager
import com.flare.sdk.util.ReflectionUtil
import java.io.File
import java.lang.Error
import java.lang.Throwable
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
    lateinit var commandManager: AbstractCommandManager<*, *>
    lateinit var fileManager: AbstractFileManager

    val consoleSender: CommandSender
        get() = commandManager.consoleSender

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

        val platformEntry = platform.platformType.entrypoint

        val instance: PlatformEntryPoint<*>?
        if (platformEntry.superclass == PlatformEntryPoint::class.java) {
            try {
                instance = platformEntry.constructors[0].newInstance(platform) as PlatformEntryPoint<*>

                playerManager = instance.playerManager
                commandManager = instance.commandManager
                fileManager = instance.fileManager
            } catch (e: Exception) {
                throw FlareException("An exception occurred while creating a platform entry point instance. ${e.message}")
            }
        } else instance = null
        platformEntryPoint = instance

        entryPoint.getDeclaredFields().forEach {
            if (Modifier.isFinal(it.modifiers)) return@forEach

            if (it.isAnnotationPresent(PlatformAccessor::class.java)) {
                it.isAccessible = true

                if (it.type.isAssignableFrom(Platform::class.java)) {
                    it.set(entryPointInstance, platform)
                } else if (it.type.isAssignableFrom(PlatformType::class.java)) {
                    it.set(entryPointInstance, platform.platformType)
                } else throw FlareException("Fields annotated with @PlatformAccessor should be Platform or PlatformType.")
            } else if (it.isAnnotationPresent(DataFolderAccessor::class.java)) {
                it.isAccessible = true

                if (it.type.isAssignableFrom(File::class.java)) {
                    it.set(entryPointInstance, platform.dataDirectory.toFile())
                } else if (it.type.isAssignableFrom(Path::class.java)) {
                    it.set(entryPointInstance, platform.dataDirectory)
                } else throw FlareException("Fields annotated with @DataFolderAccessor should be Path or File.")
            } else if (it.isAnnotationPresent(PluginConfigurationAccessor::class.java)) {
                it.isAccessible = true

                if (it.type.isAssignableFrom(FlareConfiguration::class.java)) {
                    it.set(entryPointInstance, configuration)
                } else throw FlareException("Fields annotated with @PluginConfigurationAccessor should be FlareConfiguration.")
            } else if (it.isAnnotationPresent(PlayerManagerAccessor::class.java)) {
                it.isAccessible = true

                if (it.type.isAssignableFrom(AbstractPlayerManager::class.java)) {
                    it.set(entryPointInstance, playerManager)
                } else throw FlareException("Fields annotated with @PlayerManagerAccessor should be AbstractPlayerManager.")
            } else if (it.isAnnotationPresent(CommandManagerAccessor::class.java)) {
                it.isAccessible = true

                if (it.type.isAssignableFrom(AbstractCommandManager::class.java)) {
                    it.set(entryPointInstance, commandManager)
                } else throw FlareException("Fields annotated with @CommandManagerAccessor should be AbstractCommandManager.")
            } else if (it.isAnnotationPresent(FileManagerAccessor::class.java)) {
                it.isAccessible = true

                if (it.type.isAssignableFrom(AbstractFileManager::class.java)) {
                    it.set(entryPointInstance, fileManager)
                } else throw FlareException("Fields annotated with @FileManagerAccessor should be AbstractFileManager.")
            } else if (it.isAnnotationPresent(FlareAccessor::class.java)) {
                it.isAccessible = true

                if (it.type.isAssignableFrom(Flare::class.java)) {
                    it.set(entryPointInstance, this)
                } else throw FlareException("Fields annotated with @FlareAccessor should be Flare.")
            }
        }
    }

    fun onLoad() {
        if (platformEntryPoint == null) return

        platformEntryPoint.setupAdventure()
        try {
            loadMethod?.invoke(entryPointInstance)
            PluginLoadEvent(this).call()
        } catch (e: Exception) {
            throw FlareException("An exception occurred while invoking load method. ${e.message}")
        }
    }

    fun onEnable() {
        if (platformEntryPoint == null) return

        platformEntryPoint.setupEvents()
        try {
            enableMethod?.invoke(entryPointInstance)
            PluginEnableEvent(this).call()
        } catch (e: Exception) {
            throw FlareException("An exception occurred while invoking enable method. ${e.message}")
        }
    }

    fun onDisable() {
        if (platformEntryPoint == null) return

        platformEntryPoint.disableAdventure()
        try {
            disableMethod?.invoke(entryPointInstance)
            PluginDisableEvent(this).call()
        } catch (e: Exception) {
            throw FlareException("An exception occurred while invoking disable method. ${e.message}")
        }
    }

    companion object {
        private var _instance: Flare? = null

        val instance: Flare
            get() = _instance ?: throw IllegalStateException("Flare has not been initialized yet.")

        val commandManager: AbstractCommandManager<*, *>
            get() = _instance?.commandManager ?: throw IllegalStateException("Flare has not been initialized yet.")

        val consoleSender: CommandSender
            get() = _instance?.commandManager?.consoleSender ?: throw IllegalStateException("Flare has not been initialized yet.")

        val playerManager: AbstractPlayerManager<*>
            get() = _instance?.playerManager ?: throw IllegalStateException("Flare has not been initialized yet.")

        val fileManager: AbstractFileManager
            get() = _instance?.fileManager ?: throw IllegalStateException("Flare has not been initialized yet.")

    }

}