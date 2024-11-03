package cz.janvesely.nba.ui.screens.players

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import cz.janvesely.nba.R
import cz.janvesely.nba.network.mock.fake
import cz.janvesely.nba.network.model.Player
import cz.janvesely.nba.ui.Navigation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


@Composable
fun ScreenPlayers(navController: NavHostController) {
    val viewmodel = hiltViewModel<ScreenPlayersViewModel>()

    ScreenContent(
        playersFlow = viewmodel.items,
        onNavigateToPlayerDetail = {
            navController.navigate(Navigation.PlayerDetail(it.id))
        }
    )
}

@Preview
@Composable
private fun ScreenPlayers_Preview() {
    val players = buildList {
        repeat(10) {
            add(Player.fake(it.toLong()))
        }
    }

    ScreenContent(
        playersFlow = flowOf(PagingData.from(players)),
        onNavigateToPlayerDetail = {}
    )
}


@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class
)
@Composable
private fun ScreenContent(
    playersFlow: Flow<PagingData<Player>>,
    onNavigateToPlayerDetail: (Player) -> Unit
) {
    val players = playersFlow.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("NBA Players")
                }
            )
        },

        content = { paddingValues ->
            val refreshing =
                players.loadState.refresh is LoadState.Loading && players.itemCount != 0

            val pullToRefreshState = rememberPullRefreshState(refreshing, {
                players.refresh()
            })

            Box(
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .pullRefresh(pullToRefreshState)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {

                    if (players.loadState.prepend is LoadState.Loading) {
                        items(5) {
                            ShimmerListItem()
                        }
                    }

                    val isError =   players.loadState.refresh is LoadState.Error
                                ||  players.loadState.append is LoadState.Error
                                ||  players.loadState.prepend is LoadState.Error

                    if (isError) {
                        item {
                            LoadError(onRefresh = { players.refresh() })
                        }
                    } else {
                        items(
                            players.itemCount,
                            key = players.itemKey { it.id }
                        ) { index ->

                            players[index]?.let {
                                Player(
                                    player = it,
                                    onClick = onNavigateToPlayerDetail
                                )
                            }
                        }
                    }

                    if (players.loadState.append is LoadState.Loading || players.loadState.refresh is LoadState.Loading) {
                        items(5) {
                            ShimmerListItem()
                        }
                    }
                }

                PullRefreshIndicator(
                    refreshing,
                    pullToRefreshState,
                    Modifier.align(Alignment.TopCenter)
                )
            }

        }
    )
}

@Composable
fun LoadError(onRefresh: () -> Unit) {
    Card {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .padding(start = 8.dp)
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                text = "Whops, something broke. Try again",
            )

            TextButton(
                onClick = onRefresh
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh"
                    )

                    Text(text = "Refresh data")
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Player(player: Player, onClick: (Player) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick(player) }
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                model = player.photo,
                contentDescription = "Player",
                modifier = Modifier.size(40.dp),
                loading = placeholder(R.drawable.player),
            )

            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    fontWeight = FontWeight.Bold,
                    text = "${player.first_name} ${player.last_name}",
                )

                Text(
                    text = "Position: ${player.position}, team: ${player.team?.name}",
                )
            }
        }
    }

}

@Composable
fun ShimmerListItem() {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition()

    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim, translateAnim),
        end = Offset(translateAnim + 1000f, translateAnim + 1000f)
    )

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(brush)
            )

            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(15.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(15.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush)
                )
            }
        }
    }
}