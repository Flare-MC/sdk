package com.flare.sdk


/*
 * Project: sdk
 * Created at: 30/09/2024 21:14
 * Created by: Dani-error
 */
data class FlareConfiguration(
    val name: String,
    val description: String,
    val version: String,
    val website: String,
    val authors: List<String>,
    val dependencies: List<String>,
    val optionalDependencies: List<String>
)