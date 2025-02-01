package com.flare.sdk.task

import java.util.concurrent.TimeUnit


/*
 * Project: sdk
 * Created at: 1/2/25 23:36
 * Created by: Dani-error
 */
abstract class AbstractTaskManager {

    abstract fun runAsync(runnable: Runnable): ITask

    abstract fun schedule(runnable: Runnable, delay: Long = 0L, repeat: Long, timeUnit: TimeUnit = TimeUnit.SECONDS): ITask

    abstract fun scheduleLater(runnable: Runnable, delay: Long = 0L, timeUnit: TimeUnit = TimeUnit.SECONDS): ITask

    abstract fun cancel(task: ITask)


}