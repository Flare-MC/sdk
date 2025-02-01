package com.flare.sdk.velocity.player

import com.flare.sdk.player.AbstractPlayerManager
import com.velocitypowered.api.proxy.Player
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
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
                get() = player.username

            override fun sendMessage(message: Component) =
                player.sendMessage(message)

            override fun sendMessage(message: String) {
                player.sendPlainMessage(message)
            }

            override fun sendTranslatedMessage(message: String) {
                player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message))
            }

        }
    }
}