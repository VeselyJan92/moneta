package cz.janvesely.nba.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Team(
    val id: Long,
    val conference: String?,
    val division: String?,
    val city: String?,
    val name: String?,
    val full_name: String?,
    val abbreviation: String?
)