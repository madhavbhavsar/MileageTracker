package com.dice.mileagetracker.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import com.dice.mileagetracker.utils.MyPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mPref: MyPreference
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    var updateJourneyId: Int
        get() = mPref.journeyIdPref
        set(value) { mPref.journeyIdPref = value }

    var updateTrackingStartTime: Long
        get() = mPref.trackingStartTime
        set(value) { mPref.trackingStartTime = value }

    var updateIsTracking: Boolean
        get() = mPref.isTracking
        set(value) { mPref.isTracking = value }

    var updateIsTrackingPaused: Boolean
        get() = mPref.isTrackingPaused
        set(value) { mPref.isTrackingPaused = value }

    fun updateJourneyState(flag: JourneyState) {
        _uiState.update {
            it.copy(journeyState = flag)
        }
    }

    fun startEnabled(flag: Boolean) {
        _uiState.update {
            it.copy(startEnabled = flag)
        }
    }

    fun stopEnabled(flag: Boolean) {
        _uiState.update {
            it.copy(stopEnabled = flag)
        }
    }

    fun pauseResEnabled(flag: Boolean) {
        _uiState.update {
            it.copy(pauseResEnabled = flag)
        }
    }

}

data class HomeState(
    val journeyState: JourneyState = JourneyState.New,
    val startEnabled: Boolean = true,
    val pauseResEnabled: Boolean = false,
    val stopEnabled: Boolean = false,
)

enum class JourneyState {
    New,
    Started,
    Paused,
    Resumed,
    Stop
}