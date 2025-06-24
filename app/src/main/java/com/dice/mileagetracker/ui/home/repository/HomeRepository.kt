package com.dice.mileagetracker.ui.home.repository

import com.dice.mileagetracker.data.LocationDao
import com.dice.mileagetracker.data.LocationEntity
import javax.inject.Inject

class HomeRepository @Inject constructor(private val locationDao: LocationDao) {
    suspend fun insertLocation(location: LocationEntity) {
        locationDao.insertLocation(location)
    }
}