package com.dice.mileagetracker.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocationEntity::class], version = 2)
abstract class LocationDb : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}