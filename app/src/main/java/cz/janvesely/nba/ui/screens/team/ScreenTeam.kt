package cz.janvesely.nba.ui.screens.team

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import cz.janvesely.nba.network.mock.fake
import cz.janvesely.nba.network.model.Team
import cz.janvesely.nba.ui.theme.NBAPlayersTheme


@Composable
fun ScreenTeam(navController: NavHostController) {
    val viewmodel = hiltViewModel<ScreenTeamViewModel>()

    val screenState by viewmodel.state.collectAsState()

    ScreenContent(
        state = screenState,
    )
}

@Preview
@Composable
private fun ScreenTeam_Preview() = NBAPlayersTheme {

    ScreenContent(
        state = TeamScreenState(
            team = Team.fake(1),
        ),
    )
}

@OptIn(
    ExperimentalMaterial3Api::class,
)
@Composable
private fun ScreenContent(
    state: TeamScreenState,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Club detail")
                }
            )
        },

        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = spacedBy(8.dp)
                ) {
                    item {
                        if (state.team != null) {
                            Card {
                                Column(
                                    Modifier.padding(8.dp)
                                ) {
                                    Text(
                                        text = state.team.full_name ?: "",
                                        style = MaterialTheme.typography.headlineSmall
                                    )


                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp)
                                    ) {

                                        state.team.city?.let {
                                            Text(
                                                text = it,
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        }

                                        Spacer(Modifier.weight(1f))

                                        state.team.division?.let {
                                            Text(
                                                text = it,
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        }

                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    )
}