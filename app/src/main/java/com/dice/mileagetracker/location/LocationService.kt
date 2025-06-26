package com.dice.mileagetracker.location

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.dice.mileagetracker.R
import com.dice.mileagetracker.data.LocationEntity
import com.dice.mileagetracker.data.LocationRepository
import com.dice.mileagetracker.utils.Constants
import com.dice.mileagetracker.utils.Constants.printLocationTime
import com.dice.mileagetracker.utils.MyPreference
import com.dice.mileagetracker.utils.Notification
import com.dice.mileagetracker.utils.formatElapsedTime
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@AndroidEntryPoint
class LocationService : Service() {

    @Inject
    lateinit var myRepository: LocationRepository

    @Inject
    lateinit var mPref: MyPreference

    private var lastLocation: Location? = null
    private var startTime: Long = 0L
    private var elapsedTime: String = Constants.EMPTY

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        startTime = System.currentTimeMillis()
        val notification = NotificationCompat.Builder(this, Notification.CHANNEL_ID)
            .setContentTitle(Constants.TRACKING_LOCATION)
            .setContentText(printLocationTime())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setOngoing(true)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        serviceScope.launch {
            try {
                while (true) {
                    val elapsed = (System.currentTimeMillis() - startTime) / 1000
                    val formatted = formatElapsedTime(elapsed)
                    elapsedTime = formatted
                    val lat = lastLocation?.latitude ?: 0.0
                    val lng = lastLocation?.longitude ?: 0.0

                    notification.setContentText(printLocationTime(
                        lat = lat.toString(),
                        long = lng.toString(),
                        time = formatted,
                    ))
                    notificationManager.notify(1, notification.build())
                    delay(1000L)
                }
            } catch (e: CancellationException) {
                // Timer cancelled when service stops
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        locationClient
            .getLocationUpdates()
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                if (!isValidLocation(location)) return@onEach
                lastLocation = location

                serviceScope.launch {
                    myRepository.insertLocation(
                        LocationEntity(
                            journeyId = mPref.journeyIdPref.toLong(),
                            latitude = location.latitude,
                            longitude = location.longitude,
                            timestamp = System.currentTimeMillis()
                        )
                    )
                }
            }
            .launchIn(serviceScope)

        startForeground(1, notification.build())
    }

    private fun stop() {
        serviceScope.launch {
            myRepository.insertLocation(
                LocationEntity(
                    journeyId = mPref.journeyIdPref.toLong(),
                    latitude = lastLocation?.latitude?.toDouble() ?: 0.0,
                    longitude = lastLocation?.longitude?.toDouble() ?: 0.0,
                    timestamp = System.currentTimeMillis(),
                    elapsedTime = elapsedTime
                )
            )
        }
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"

    }

    private fun isValidLocation(newLocation: Location): Boolean {
        val accuracy = newLocation.accuracy
        if (accuracy > 30f) return false

        lastLocation?.let { last ->
            val distance = last.distanceTo(newLocation)
            val timeDiff = newLocation.time - last.time
            if (distance > 1000 || timeDiff < 1000) return false
        }

        return true
    }
}