package cz.janvesely.nba.network.requests.base

import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    val next_cursor: Int,
    val per_page: Int
)