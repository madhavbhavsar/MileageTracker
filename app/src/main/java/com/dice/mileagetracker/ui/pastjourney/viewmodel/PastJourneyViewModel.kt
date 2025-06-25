package com.dice.mileagetracker.ui.pastjourney.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PastJourneyViewModel @Inject constructor(): ViewModel(){

}

data class JourneyModel2(
    val journerID:String,
    val journeyKms:String,
    val journeyMiles:String,
    val journetTime:String,
)