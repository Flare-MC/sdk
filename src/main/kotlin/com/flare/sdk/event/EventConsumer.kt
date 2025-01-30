package com.flare.sdk.event

import java.lang.reflect.Method
import java.util.function.Consumer


/*
 * Project: sdk
 * Created at: 14/01/2025 21:39
 * Created by: Dani-error
 */
class EventConsumer<T : Event>(val `object`: Any, private val method: Method) : Consumer<T> {

    init {
        method.isAccessible = true
    }

    override fun accept(t: T) {
        try {
            this.method.invoke(this.`object`, t)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
    }

}