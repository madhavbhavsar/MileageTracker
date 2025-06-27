package com.dice.mileagetracker

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dice.mileagetracker.location.LocationService
import com.dice.mileagetracker.ui.theme.MileageTrackerTheme
import com.dice.mileagetracker.utils.MyPreference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var mPref: MyPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MileageTrackerTheme {
                MileageTrackerApp()
            }
        }
    }

    override fun onDestroy() {
        Intent(applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_STOP
            applicationContext.startService(this)
        }
        super.onDestroy()
    }
}