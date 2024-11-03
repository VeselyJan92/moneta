package cz.janvesely.nba.ui.screens.player

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cz.janvesely.nba.repository.NBARepository
import cz.janvesely.nba.ui.Navigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenPlayerViewModel @Inject constructor(
    val nbaRepository: NBARepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val navModel = savedStateHandle.toRoute<Navigation.PlayerDetail>()

    private val _state = MutableStateFlow(PlayerScreenState())
    val state: StateFlow<PlayerScreenState> = _state

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(player = nbaRepository.getPlayer(navModel.id).data)
            }
        }
    }

}