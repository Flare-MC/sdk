package com.flare.sdk.command

import java.lang.reflect.Method


/*
 * Project: sdk
 * Created at: 1/2/25 19:10
 * Created by: Dani-error
 */
data class CommandDefinition(
    val executeMethod: Method, val tabCompleteMethod: Method,
    val classInstance: Any,
    val commandInfo: CommandInfo
)