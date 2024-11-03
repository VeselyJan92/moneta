package cz.janvesely.nba.ui.screens.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import cz.janvesely.nba.R
import cz.janvesely.nba.network.mock.fake
import cz.janvesely.nba.network.model.Player
import cz.janvesely.nba.network.model.Team
import cz.janvesely.nba.ui.Navigation


@Composable
fun ScreenPlayer(navController: NavHostController) {
    val viewmodel = hiltViewModel<ScreenPlayerViewModel>()

    val screenState by viewmodel.state.collectAsState()

    ScreenContent(
        state = screenState,
        goToTeam = { navController.navigate(Navigation.TeamDetail(it.id))}
    )

}

@Preview
@Composable
private fun ScreenPlayer_Preview() {
    ScreenContent(
        state = PlayerScreenState(player = Player.fake(1)),
        goToTeam = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
private fun ScreenContent(
    state: PlayerScreenState,
    goToTeam: (Team) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Player detail")
                }
            )
        },
        content = { paddingValues ->
            LazyColumn(
                contentPadding = paddingValues
            ) {
                item {

                    if (state.player != null) {
                        Card(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(150.dp)
                                    .fillMaxWidth()
                                    .padding(end = 8.dp, top = 8.dp, start = 8.dp)
                            ){
                                Column(
                                    Modifier.fillMaxHeight().padding(bottom = 8.dp)
                                ) {
                                    Text(text = state.player.fullName , style = MaterialTheme.typography.headlineSmall)

                                    state.player.team?.let {
                                        TextButton(
                                            contentPadding = PaddingValues(0.dp),
                                            onClick = { goToTeam(state.player.team)}) {
                                            Text(text = it.name ?:  "" )
                                        }
                                    }
                                    
                                    Spacer(Modifier.weight(1f))

                                    state.player.height?.let {
                                        Text(text = "Height: " + it)
                                    }

                                    state.player.position?.let {
                                        Text(text = "Position: " + it)
                                    }
                                }

                                GlideImage(
                                    modifier = Modifier.align(Alignment.BottomEnd),
                                    model = state.player.photo,
                                    contentDescription = "Player",
                                    loading = placeholder(R.drawable.player),
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}


