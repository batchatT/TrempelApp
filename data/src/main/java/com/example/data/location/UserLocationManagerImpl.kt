package com.example.data.location

import android.content.Context
import android.location.LocationManager
import javax.inject.Inject

class UserLocationManagerImpl @Inject constructor(
    private val context: Context
) : UserLocationManager {

    override suspend fun isGpsEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}
