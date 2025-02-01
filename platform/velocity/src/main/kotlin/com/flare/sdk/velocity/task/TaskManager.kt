package com.flare.sdk.velocity.task

import com.flare.sdk.task.AbstractTaskManager
import com.flare.sdk.task.ITask
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.scheduler.ScheduledTask
import java.util.concurrent.TimeUnit


/*
 * Project: sdk
 * Created at: 1/2/25 23:47
 * Created by: Dani-error
 */
class TaskManager(private val plugin: Any, private val proxyServer: ProxyServer) : AbstractTaskManager() {

    override fun runAsync(runnable: Runnable): ITask {
        return wrap(proxyServer.scheduler.buildTask(plugin, runnable)
            .schedule())
    }

    override fun schedule(runnable: Runnable, delay: Long, repeat: Long, timeUnit: TimeUnit): ITask {
        return wrap(proxyServer.scheduler.buildTask(plugin, runnable)
            .delay(delay, timeUnit)
            .repeat(repeat, timeUnit)
            .schedule())
    }

    override fun scheduleLater(runnable: Runnable, delay: Long, timeUnit: TimeUnit): ITask {
        return wrap(proxyServer.scheduler.buildTask(plugin, runnable)
            .delay(delay, timeUnit)
            .schedule())
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