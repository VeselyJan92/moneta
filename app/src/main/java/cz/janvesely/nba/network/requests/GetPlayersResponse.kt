package cz.janvesely.nba.network.requests

import cz.janvesely.nba.network.model.Player
import cz.janvesely.nba.network.requests.base.Meta
import kotlinx.serialization.Serializable

@Serializable
data class GetPlayersResponse(
    val data: List<Player>,
    val meta: Meta
)
