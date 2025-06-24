package com.dice.mileagetracker.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    fun updateJourneyState(flag: JourneyState) {
        _uiState.update {
            it.copy(journeyState = flag)
        }
    }

    fun startEnabled(flag: Boolean){
        _uiState.update {
            it.copy(startEnabled = flag)
        }
    }
    fun stopEnabled(flag: Boolean){
        _uiState.update {
            it.copy(stopEnabled = flag)
        }
    }
    fun pauseResEnabled(flag: Boolean){
        _uiState.update {
            it.copy(pauseResEnabled = flag)
        }
    }

}

data class HomeState(
    val journeyState: JourneyState = JourneyState.New,
    val startEnabled : Boolean = true,
    val pauseResEnabled : Boolean = true,
    val stopEnabled : Boolean = true,
)

enum class JourneyState {
    New,
    Started,
    Paused,
    Resumed,
    Stop
}