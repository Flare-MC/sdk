package com.flare.sdk.annotations


/*
 * Project: sdk
 * Created at: 30/09/2024 20:31
 * Created by: Dani-error
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class PlatformAccessor

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class DataFolder

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class PluginConfiguration