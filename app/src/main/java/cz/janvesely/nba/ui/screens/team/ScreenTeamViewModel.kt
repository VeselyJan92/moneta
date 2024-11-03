package cz.janvesely.nba.ui.screens.team

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cz.janvesely.nba.network.model.Player
import cz.janvesely.nba.repository.NBARepository
import cz.janvesely.nba.ui.Navigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenTeamViewModel @Inject constructor(
    val nbaRepository: NBARepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val navModel = savedStateHandle.toRoute<Navigation.TeamDetail>()

    private val _state = MutableStateFlow(TeamScreenState())
    val state: StateFlow<TeamScreenState> = _state

    init {
        viewModelScope.launch {
            launch {
                _state.update { it.copy(team = nbaRepository.getTeam(navModel.id).data) }
            }
        }
    }

}