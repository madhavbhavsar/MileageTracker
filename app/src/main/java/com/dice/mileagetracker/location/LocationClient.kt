package com.dice.mileagetracker.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocationUpdates(): Flow<Location>
    fun getLastLocation(callback: (Location?) -> Unit)
    class LocationException(message: String): Exception()
}