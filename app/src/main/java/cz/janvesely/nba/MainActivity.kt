package cz.janvesely.nba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.janvesely.nba.ui.Navigation
import cz.janvesely.nba.ui.screens.player.ScreenPlayer
import cz.janvesely.nba.ui.screens.players.ScreenPlayers
import cz.janvesely.nba.ui.screens.team.ScreenTeam
import cz.janvesely.nba.ui.screens.team.TeamScreenState
import cz.janvesely.nba.ui.theme.NBAPlayersTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NBAPlayersTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Navigation.Players) {
                    composable<Navigation.Players> {
                        ScreenPlayers(navController)
                    }

                    composable<Navigation.PlayerDetail> {
                        ScreenPlayer(navController)
                    }

                    composable<Navigation.TeamDetail> {
                        ScreenTeam(navController)
                    }
                }
            }
        }
    }
}