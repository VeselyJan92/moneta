package cz.janvesely.nba.network.model

import cz.janvesely.nba.network.requests.base.Meta
import kotlinx.serialization.Serializable


@Serializable
data class Player(
    val id: Long,
    val first_name: String?,
    val last_name: String?,
    val position: String?,
    val height: String?,
    val weight: Float?,
    val jersey_number: String?,
    val college: String?,
    val country: String?,
    val draft_year: Int?,
    val draft_round: Int?,
    val draft_number: Int?,
    val team: Team?
){

    val photo: String = "https://storage.googleapis.com/nbarankings-theringer-com-cms/public/media/ringerbasketballhub/players/GiannisAntetekounmpo-1728920065023-small.png"

    val fullName: String
        get() = "$first_name $last_name"

}


