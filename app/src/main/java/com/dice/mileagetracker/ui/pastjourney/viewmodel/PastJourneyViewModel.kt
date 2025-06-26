package com.dice.mileagetracker.ui.pastjourney.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dice.mileagetracker.data.LocationRepository
import com.dice.mileagetracker.utils.calculateDuration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PastJourneyViewModel @Inject constructor(
    val repository: LocationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PastJourneyState())
    val uiState: StateFlow<PastJourneyState> = _uiState.asStateFlow()

    lateinit var journeyList: List<JourneyModel>

    init {
        fetchDataFromDB()
    }

    fun fetchDataFromDB() {
        viewModelScope.launch {
            updateLoadingState(true)
            val locations = repository.fetchAllLocation()
            journeyList = locations.groupBy { it.journeyId } // Group by journeyId
                .map { (journeyId, points) ->
                    JourneyModel(
                        journeyId = journeyId.toString(),
                        journeyPoints = points.map {
                            LatLong(
                                latitude = it.latitude,
                                longitude = it.longitude
                            )
                        },
                        journeyDuration = calculateDuration(points)
                    )
                }

        }.invokeOnCompletion {
            updateLoadingState(false)
            updateJourneyList(journeyList)
        }
    }
    fun updateJourneyList(flag: List<JourneyModel>) {
        _uiState.update {
            it.copy(
                journeyList = flag
            )
        }
    }

    fun updateLoadingState(flag: Boolean){
        _uiState.update {
            it.copy(
                isLoading = flag
            )
        }
    }
}

data class PastJourneyState(
    val isLoading: Boolean = false,
    val journeyList: List<JourneyModel> = emptyList()
)

data class JourneyModel(
    val journeyId: String? = null,
    val journeyPoints: List<LatLong>? = null,
    val journeyMiles: String? = null,
    val journeyKms: String? = null,
    val journeyDuration: String? = null
)

data class LatLong(
    val latitude: Double? = null,
    val longitude: Double? = null,
)