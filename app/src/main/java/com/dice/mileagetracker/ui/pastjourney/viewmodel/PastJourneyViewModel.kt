package com.dice.mileagetracker.ui.pastjourney.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dice.mileagetracker.data.LocationRepository
import com.dice.mileagetracker.utils.Constants
import com.dice.mileagetracker.utils.haversineDistance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import javax.inject.Inject

@HiltViewModel
class PastJourneyViewModel @Inject constructor(
    val repository: LocationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PastJourneyState())
    val uiState: StateFlow<PastJourneyState> = _uiState.asStateFlow()

    var journeyList: List<JourneyModel>? = null

    init {
        fetchDataFromDB()
    }

    fun fetchDataFromDB() {
        Log.i("FetchDB", "fetchDataFromDB: 1")
        viewModelScope.launch {
            delay(1000L) // delay for smooth transition
            updateLoadingState(true)
            Log.i("FetchDB", "fetchDataFromDB: 2")
            val locations = repository.fetchAllLocation()
            journeyList = locations.groupBy { it.journeyId } // Group by journeyId
                .map { (journeyId, points) ->
                    Log.i("FetchDB", "fetchDataFromDB: 3")

                    val latLongs = points.map {
                        LatLong(
                            latitude = it.latitude,
                            longitude = it.longitude
                        )
                    }
                    val totalDistanceKm = latLongs.zipWithNext { a, b ->
                        haversineDistance(a.latitude?:0.0, a.longitude?:0.0, b.latitude?:0.0, b.longitude?:0.0)
                    }.sum()

                    val totalDistanceMiles = totalDistanceKm * Constants.MILES_KMS
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
                }

        }.invokeOnCompletion {
            Log.i("FetchDB", "fetchDataFromDB: 5 ${journeyList.toString()} ")
            updateLoadingState(false)
            updateJourneyList(journeyList)
            Log.i("FetchDB", "fetchDataFromDB:6 ")
        }
    }

    fun updateJourneyList(flag: List<JourneyModel>?) {
        _uiState.update {
            it.copy(
                journeyList = flag
            )
        }
    }

    fun updateLoadingState(flag: Boolean) {
        _uiState.update {
            it.copy(
                isLoading = flag
            )
        }
    }
}

data class PastJourneyState(
    val isLoading: Boolean = true,
    val journeyList: List<JourneyModel>? = emptyList<JourneyModel>()
)

@Serializable
data class JourneyModel(
    val journeyId: String? = null,
    val journeyPoints: List<LatLong>? = null,
    val journeyMiles: String? = null,
    val journeyKms: String? = null,
    val journeyDuration: String? = null
)

@Serializable
data class LatLong(
    val latitude: Double? = null,
    val longitude: Double? = null,
)