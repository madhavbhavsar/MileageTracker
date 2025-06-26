package com.dice.mileagetracker.di

import android.content.Context
import androidx.room.Room
import com.dice.mileagetracker.data.LocationDao
import com.dice.mileagetracker.data.LocationDb
import com.dice.mileagetracker.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LocationDb {
        return Room.databaseBuilder(
            context,
            LocationDb::class.java,
            Constants.JOURNEY_DB
        ).fallbackToDestructiveMigration(false).build()
    }

    @Provides
    fun provideLocationDao(appDatabase: LocationDb): LocationDao {
        return appDatabase.locationDao()
    }
}