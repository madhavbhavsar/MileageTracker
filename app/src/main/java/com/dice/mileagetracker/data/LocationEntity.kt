package com.dice.mileagetracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dice.mileagetracker.utils.Constants

@Entity(tableName = Constants.LOCATION_TABLE)
data class LocationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val journeyId: Long,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,
    val elapsedTime: String? = null,
)