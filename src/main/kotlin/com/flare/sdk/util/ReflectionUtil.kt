package com.flare.sdk.util

import java.lang.reflect.Method


/*
 * Project: sdk
 * Created at: 30/9/24 15:53
 * Created by: Dani-error
 */
object ReflectionUtil {

    fun getMethod(clazz: Class<*>, name: String, vararg params: Class<*>): Method? {
        return try {
            clazz.getDeclaredMethod(name, *params)
        } catch (e: Exception) {
            null
        }
    }

}