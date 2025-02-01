package com.flare.sdk.command


/*
 * Project: sdk
 * Created at: 1/2/25 18:39
 * Created by: Dani-error
 */
interface CommandLocaleHandler {

    val noPermissions: String
        get() = "&cYou have no permissions to execute that command!"

    val executedByConsole: String
        get() = "&cThis command is exclusive to players!"

}