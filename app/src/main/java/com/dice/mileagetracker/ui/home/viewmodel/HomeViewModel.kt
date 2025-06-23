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

}

data class HomeState(
    val journeyState: JourneyState = JourneyState.New
)

enum class JourneyState {
    New,
    Started,
    Paused,
    Stop
}