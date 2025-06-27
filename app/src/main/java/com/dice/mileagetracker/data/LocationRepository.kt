package com.dice.mileagetracker.data

import javax.inject.Inject

class LocationRepository @Inject constructor(private val locationDao: LocationDao) {
    suspend fun insertLocation(location: LocationEntity) {
        locationDao.insertLocation(location)
    }

    suspend fun fetchAllLocation(): List<LocationEntity> {
        return locationDao.fetchAllLocations()
    }

    suspend fun getLocationsByJourney(journeyID:Int): List<LocationEntity> {
        return locationDao.getLocationsByJourney(journeyID)
    }
}