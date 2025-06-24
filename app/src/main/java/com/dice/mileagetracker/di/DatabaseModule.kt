package com.dice.mileagetracker.di

import android.content.Context
import androidx.room.Room
import com.dice.mileagetracker.data.LocationDao
import com.dice.mileagetracker.data.LocationDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LocationDb {
        return Room.databaseBuilder(
            context,
            LocationDb::class.java,
            "journey_db"
        ).build()
    }

    @Provides
    fun provideLocationDao(appDatabase: LocationDb): LocationDao {
        return appDatabase.locationDao()
    }
}