package cz.janvesely.nba.ui

import kotlinx.serialization.Serializable

@Serializable
sealed interface Navigation {

    @Serializable
    data object Players : Navigation

    @Serializable
    data class PlayerDetail(val id: Long) : Navigation

    @Serializable
    data class TeamDetail(val id: Long) : Navigation

}