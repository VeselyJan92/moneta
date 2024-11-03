package cz.janvesely.nba.network.mock

import cz.janvesely.nba.network.model.Player
import cz.janvesely.nba.network.model.Team

fun Player.Companion.fake(id: Long) = Player(
    id = id,
    first_name = "Name",
    last_name = "Surname",
    position = "G",
    height = "6-6",
    weight = 100f,
    jersey_number = null,
    college = null,
    country = "CZ",
    draft_year = 2024,
    draft_round = 1,
    draft_number = null,
    team = Team(
        1, conference = "", division = "", name = "TeamName", city = "City", full_name = "Full name", abbreviation = " abbreviation"
    )
)

fun Team.Companion.fake(id: Long) = Team(
    id = id,
    conference = "East",
    division = "Atlantic",
    city = "Philadelphia",
    name = "76ers",
    full_name = "Philadelphia 76ers",
    abbreviation = "PHI"
)
