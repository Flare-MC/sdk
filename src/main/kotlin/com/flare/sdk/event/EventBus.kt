@file:Suppress("UNCHECKED_CAST")

package com.flare.sdk.event

import com.flare.sdk.event.api.Subscribe
import java.lang.reflect.Method
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import java.util.function.Consumer


/*
 * Project: sdk
 * Created at: 14/01/2025 21:44
 * Created by: Dani-error
 */
object EventBus {

    private val registeredEvents: ConcurrentHashMap<Class<out Event>, CopyOnWriteArrayList<Consumer<out Event>>> =
        ConcurrentHashMap()

    fun register(instance: Any) {
        getMethods(instance).forEach { method ->
            registeredEvents.computeIfAbsent(method.parameterTypes[0] as Class<out Event>) { CopyOnWriteArrayList() }
                .add(EventConsumer(instance, method))
        }
    }

    fun unregister(instance: Any) {
        getMethods(instance).forEach { method ->
            val eventType = method.parameterTypes[0] as Class<*>
            registeredEvents[eventType]?.removeIf { consumer ->
                consumer is EventConsumer<*> && consumer.`object` == instance
            }
        }
    }

    private fun getMethods(instance: Any): List<Method> {
        return instance.javaClass.declaredMethods.filter { method ->
            method.isAnnotationPresent(Subscribe::class.java) &&
                    method.parameterCount == 1 &&
                    Event::class.java.isAssignableFrom(method.parameterTypes[0])
        }
    }

    fun <T : Event> register(eventType: Class<T>, consumer: Consumer<T>): Boolean {
        return registeredEvents.computeIfAbsent(eventType) { CopyOnWriteArrayList() }.add(consumer)
    }

    fun <T : Event> unregister(eventType: Class<T>, consumer: Consumer<T>): Boolean {
        return registeredEvents[eventType]?.remove(consumer) ?: false
    }

    @JvmStatic
    fun <T : Event> post(event: T): T {
        try {
            registeredEvents[event.javaClass]?.forEach { consumer ->
                (consumer as Consumer<T>).accept(event)
            }
        } catch (throwable: Throwable) {
            if (throwable is AbstractMethodError || throwable is IllegalAccessError) {
                throw throwable
            }

            throwable.printStackTrace()
        }
        return event
    }

}