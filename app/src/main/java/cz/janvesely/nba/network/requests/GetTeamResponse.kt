package cz.janvesely.nba.network.requests

import cz.janvesely.nba.network.model.Team
import kotlinx.serialization.Serializable

@Serializable
data class GetTeamResponse(
    val data: Team,
)
