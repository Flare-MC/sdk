package com.flare.sdk

import com.flare.sdk.platform.Platform
import com.flare.sdk.util.ReflectionUtil
import java.lang.reflect.Method


/*
 * Project: sdk
 * Created at: 30/9/24 15:25
 * Created by: Dani-error
 */
@Suppress("unused")
class Flare(val platform: Platform, entryPoint: Class<*>) {

    private val entryPointInstance: Any

    private val loadMethod: Method?
    private val enableMethod: Method?
    private val disableMethod: Method?

    init {
        if (instance == null) {
            instance = this
            try {
                entryPointInstance = entryPoint.getConstructor().newInstance()
            } catch (e: Exception) {
                throw FlareException("An exception occurred while creating an entry point instance. ${e.message}")
            }

            loadMethod = ReflectionUtil.getMethod(entryPoint, "onLoad")
            enableMethod = ReflectionUtil.getMethod(entryPoint, "onEnable")
            disableMethod = ReflectionUtil.getMethod(entryPoint, "onDisable")
        } else throw FlareException("Cannot be two instances of Flare.")
    }

    fun onLoad() {
        try {
            loadMethod?.invoke(entryPointInstance)
        } catch (e: Exception) {
            throw FlareException("An exception occurred while invoking load method. ${e.message}")
        }
    }

    fun onEnable() {
        try {
            enableMethod?.invoke(entryPointInstance)
        } catch (e: Exception) {
            throw FlareException("An exception occurred while invoking enable method. ${e.message}")
        }
    }

    fun onDisable() {
        try {
            disableMethod?.invoke(entryPointInstance)
        } catch (e: Exception) {
            throw FlareException("An exception occurred while invoking disable method. ${e.message}")
        }
    }

    companion object {

        var instance: Flare? = null

    }
}