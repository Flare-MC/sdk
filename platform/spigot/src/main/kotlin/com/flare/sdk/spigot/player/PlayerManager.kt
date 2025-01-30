package com.flare.sdk.spigot.player

import com.flare.sdk.player.AbstractPlayerManager
import org.bukkit.entity.Player
import java.util.*


/*
 * Project: sdk
 * Created at: 30/01/2025 20:32
 * Created by: Dani-error
 */
class PlayerManager : AbstractPlayerManager<Player>() {
    override fun bindPlayer(player: Player): com.flare.sdk.player.Player {
        return object : com.flare.sdk.player.Player {

            override val uuid: UUID
                get() = player.uniqueId

            override val name: String
                get() = player.name

        }
    }
}