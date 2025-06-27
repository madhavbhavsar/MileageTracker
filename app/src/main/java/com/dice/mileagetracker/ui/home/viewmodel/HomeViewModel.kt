package com.dice.mileagetracker.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dice.mileagetracker.data.LocationRepository
import com.dice.mileagetracker.ui.pastjourney.viewmodel.JourneyModel
import com.dice.mileagetracker.ui.pastjourney.viewmodel.LatLong
import com.dice.mileagetracker.utils.Constants
import com.dice.mileagetracker.utils.MyPreference
import com.dice.mileagetracker.utils.haversineDistance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.component1
import kotlin.collections.component2

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mPref: MyPreference,
    val repository: LocationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    var updateJourneyId: Int
        get() = mPref.journeyIdPref
        set(value) {
            mPref.journeyIdPref = value
        }

    var updateTrackingStartTime: Long
        get() = mPref.trackingStartTime
        set(value) {
            mPref.trackingStartTime = value
        }

    var updateIsTracking: Boolean
        get() = mPref.isTracking
        set(value) {
            mPref.isTracking = value
        }

    var updateIsTrackingPaused: Boolean
        get() = mPref.isTrackingPaused
        set(value) {
            mPref.isTrackingPaused = value
        }

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

    fun updateLoading(flag: Boolean) {
        _uiState.update {
            it.copy(isLoading = flag)
        }
    }

    fun showDialog(flag: JourneyModel?) {
        _uiState.update {
            it.copy(showDialog = flag)
        }
    }

    fun fetchCurrentJourney() {
        viewModelScope.launch {
            updateLoading(true)
            delay(1000L)// delay for smooth transition
            val locations = repository.getLocationsByJourney(updateJourneyId)
            val journeyList = locations.groupBy { it.journeyId } // Group by journeyId
                .map { (journeyId, points) ->
                    val latLongs = points.map {
                        LatLong(
                            latitude = it.latitude,
                            longitude = it.longitude
                        )
                    }
                    val totalDistanceKm = latLongs.zipWithNext { a, b ->
                        haversineDistance(
                            a.latitude ?: 0.0,
                            a.longitude ?: 0.0,
                            b.latitude ?: 0.0,
                            b.longitude ?: 0.0
                        )
                    }.sum()

                    val totalDistanceMiles = totalDistanceKm * Constants.MILES_KMS

                    showDialog(
                        JourneyModel(
                            journeyId = journeyId.toString(),
                            journeyPoints = points.map {
                                LatLong(
                                    latitude = it.latitude,
                                    longitude = it.longitude
                                )
                            },
                            journeyKms = Constants.DECIMAL_FORMAT.format(totalDistanceKm),
                            journeyMiles = Constants.DECIMAL_FORMAT.format(totalDistanceMiles),
                            journeyDuration = points.last().elapsedTime.toString()
                        )
                    )

                }
        }.invokeOnCompletion {
            updateLoading(false)
        }
    }

}

data class HomeState(
    val journeyState: JourneyState = JourneyState.New,
    val startEnabled: Boolean = true,
    val pauseResEnabled: Boolean = false,
    val stopEnabled: Boolean = false,
    val showDialog: JourneyModel? = null,
    val isLoading: Boolean = false,
)

enum class JourneyState {
    New,
    Started,
    Paused,
    Resumed,
    Stop
}