package com.dice.mileagetracker.di

import android.content.Context
import android.content.SharedPreferences
import com.dice.mileagetracker.utils.MyPreference
import com.dice.mileagetracker.utils.PrefKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            PrefKey.PREFERENCE_NAME, Context.MODE_PRIVATE
        )
    }

    @Singleton
    @Provides
    fun provideMyPreference(mSharedPreferences: SharedPreferences) =
        MyPreference(mSharedPreferences)

}
