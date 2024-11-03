package cz.janvesely.nba.network.api

import cz.janvesely.nba.network.requests.GetPlayerResponse
import cz.janvesely.nba.network.requests.GetPlayersResponse
import cz.janvesely.nba.network.requests.GetTeamResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NbaAPI {

    @GET("players")
    suspend fun getPlayers(
        @Query("cursor")  cursor: Int,
        @Query("per_page")  perPage: Int,
    ): GetPlayersResponse

    @GET("players/{id}")
    suspend fun getPlayer(@Path("id") id: Long): GetPlayerResponse


    @GET("teams/{id}")
    suspend fun getTeam(@Path("id") id: Long): GetTeamResponse

}