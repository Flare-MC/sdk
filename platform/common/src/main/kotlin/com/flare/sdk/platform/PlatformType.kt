package com.flare.sdk.platform


/*
 * Project: sdk
 * Created at: 30/9/24 15:23
 * Created by: Dani-error
 */
enum class PlatformType(val entrypoint: Class<*>) {
    SPIGOT(Class.forName("com.flare.sdk.spigot.SpigotPlatform")),
    BUNGEECORD(Class.forName("com.flare.sdk.bungee.BungeePlatform")),
    VELOCITY(Class.forName("com.flare.sdk.velocity.VelocityPlatform"))
}