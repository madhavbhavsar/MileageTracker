package com.dice.mileagetracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationEntity)

    @Query("SELECT * FROM location_table WHERE journeyId = :journeyId")
    suspend fun getLocationsByJourney(journeyId: Long): List<LocationEntity>
}