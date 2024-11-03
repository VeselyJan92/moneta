package cz.janvesely.nba.ui.screens.players

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cz.janvesely.nba.network.model.Player
import cz.janvesely.nba.repository.NBARepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ScreenPlayersViewModel @Inject constructor(
    val nbaRepository: NBARepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val pager = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { nbaRepository.getPlayersAsPagingSource() }
    )

    val items: Flow<PagingData<Player>> = pager.flow.cachedIn(viewModelScope)

}