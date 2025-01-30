package com.flare.sdk.event

import java.util.function.Consumer


/*
 * Project: sdk
 * Created at: 14/01/2025 21:44
 * Created by: Dani-error
 */
interface EventHandler {

    fun <T : Event> handle(clazz: Class<T>, consumer: Consumer<T>) =
        EventBus.register(clazz, consumer)
}