@file:Suppress("UNCHECKED_CAST")

package com.flare.sdk.platform

import com.flare.sdk.command.AbstractCommandManager
import com.flare.sdk.file.AbstractFileManager
import com.flare.sdk.player.AbstractPlayerManager
import com.flare.sdk.task.AbstractTaskManager


/*
 * Project: sdk
 * Created at: 30/01/2025 20:28
 * Created by: Dani-error
 */
abstract class PlatformEntryPoint<T>(val platform: T) {

    abstract val type: PlatformType
    abstract val playerManager: AbstractPlayerManager<*>
    abstract val commandManager: AbstractCommandManager<T, *>
    abstract val fileManager: AbstractFileManager
    abstract val taskManager: AbstractTaskManager

    open fun setupAdventure() { }
    open fun disableAdventure() { }

    abstract fun setupEvents()

}