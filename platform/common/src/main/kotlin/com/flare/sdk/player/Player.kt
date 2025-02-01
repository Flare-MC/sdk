package com.flare.sdk.player

import com.flare.sdk.command.CommandSender
import java.util.UUID


/*
 * Project: sdk
 * Created at: 30/01/2025 20:13
 * Created by: Dani-error
 */
interface Player : CommandSender {

    val uuid: UUID
    override val name: String

}