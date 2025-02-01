package com.flare.sdk.spigot.task

import com.flare.sdk.task.AbstractTaskManager
import com.flare.sdk.task.ITask
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import java.util.concurrent.TimeUnit


/*
 * Project: sdk
 * Created at: 1/2/25 23:44
 * Created by: Dani-error
 */
class TaskManager(private val plugin: JavaPlugin) : AbstractTaskManager() {

    override fun runAsync(runnable: Runnable): ITask {
        return wrap(Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable))
    }

    override fun schedule(runnable: Runnable, delay: Long, repeat: Long, timeUnit: TimeUnit): ITask {
        return wrap(Bukkit.getScheduler().runTaskTimer(plugin, runnable, timeUnit.toSeconds(delay) * 20L, timeUnit.toSeconds(repeat) * 20L))
    }

    override fun scheduleLater(runnable: Runnable, delay: Long, timeUnit: TimeUnit): ITask {
        return wrap(Bukkit.getScheduler().runTaskLater(plugin, runnable, timeUnit.toSeconds(delay) * 20L))
    }

    override fun cancel(task: ITask) {
        task.cancel()
    }

    private fun wrap(task: BukkitTask): ITask {
        return object : ITask {
            override fun cancel() {
                task.cancel()
            }

        }
    }
}