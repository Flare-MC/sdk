package com.flare.sdk.player

import java.util.UUID


/*
 * Project: sdk
 * Created at: 30/01/2025 20:14
 * Created by: Dani-error
 */
abstract class AbstractPlayerManager<T> {

    private val players = mutableMapOf<T, Player>()

    abstract fun bindPlayer(player: T): Player

    fun addPlayer(player: T): Player {
        val binded = bindPlayer(player)
        players[player] = binded
        return binded
    }

    fun removePlayer(player: T): Player? =
        players.remove(player)

    fun getPlayer(player: T): Player? =
        players[player]

    fun getOrCreatePlayer(player: T): Player =
        getPlayer(player) ?: addPlayer(player)

    fun getPlayer(id: UUID): Player? {
        return players.values.find { it.uuid == id }
    }

    fun getPlayers(): List<Player> =
        players.values.toList()

}