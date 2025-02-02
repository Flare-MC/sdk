package com.flare.sdk.event


/*
 * Project: sdk
 * Created at: 14/01/2025 21:33
 * Created by: Dani-error
 */
open class Event {

    fun call() {
        EventBus.post(this)
    }

}

open class EventCancellable(var cancelled: Boolean = false) : Event() {

    fun cancel() {
        cancelled = true
    }

}

interface PlatformGetter {

    val original: Any

}