package cz.janvesely.nba.repository

import cz.janvesely.nba.network.api.NbaAPI
import javax.inject.Inject


class NBARepository @Inject constructor(
    private val api: NbaAPI
) {

    suspend fun getPlayers(page: Int, perPage: Int) = api.getPlayers(page, perPage)

    fun getPlayersAsPagingSource() = PlayerPagingSource(this, 35)

    suspend fun getPlayer(id: Long) = api.getPlayer(id)

    suspend fun getTeam(id: Long) = api.getTeam(id)

}