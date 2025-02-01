package com.flare.sdk.bungee.task

import com.flare.sdk.task.AbstractTaskManager
import com.flare.sdk.task.ITask
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.api.scheduler.ScheduledTask
import java.util.concurrent.TimeUnit


/*
 * Project: sdk
 * Created at: 1/2/25 23:37
 * Created by: Dani-error
 */
class TaskManager(private val plugin: Plugin) : AbstractTaskManager() {

    override fun runAsync(runnable: Runnable): ITask {
        return wrap(plugin.proxy.scheduler.runAsync(plugin, runnable))
    }

    override fun schedule(runnable: Runnable, delay: Long, repeat: Long, timeUnit: TimeUnit): ITask {
        return wrap(plugin.proxy.scheduler.schedule(plugin, runnable, delay, repeat, timeUnit))
    }

    override fun scheduleLater(runnable: Runnable, delay: Long, timeUnit: TimeUnit): ITask {
        return wrap(plugin.proxy.scheduler.schedule(plugin, runnable, delay, timeUnit))
    }

    override fun cancel(task: ITask) {
        task.cancel()
    }

    private fun wrap(task: ScheduledTask): ITask {
        return object : ITask {

            override fun cancel() {
                task.cancel()
            }

        }
    }

}