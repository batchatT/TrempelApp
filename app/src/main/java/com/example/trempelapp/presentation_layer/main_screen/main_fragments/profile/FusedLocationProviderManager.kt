package com.example.trempelapp.presentation_layer.main_screen.main_fragments.profile

import android.annotation.SuppressLint
import android.os.Looper
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.example.domain_layer.models.ProfileState
import com.example.domain_layer.models.UserLocation
import com.example.utils.SingleLiveEvent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import javax.inject.Inject

class FusedLocationProviderManager @Inject constructor() {
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null

    fun create(activity: FragmentActivity) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)

        locationRequest = LocationRequest.create()
        locationRequest?.interval = 5000
        locationRequest?.fastestInterval = 5000
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                val location = locationResult.locations.last()
                Log.d(
                    "locationtag",
                    "${location.latitude} + \",\" +\n${location.longitude}"
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocation(profileState: SingleLiveEvent<ProfileState>) {
        fusedLocationProviderClient?.lastLocation?.addOnSuccessListener() {
            it?.let {
                val currentLocation = it
                profileState.value = profileState.value?.copy(
                    location = UserLocation(currentLocation.latitude, currentLocation.longitude)
                )
                Log.d(
                    "locationtag",
                    "${currentLocation.latitude} + \",\" +\n${currentLocation.longitude}"
                )
            } ?: fusedLocationProviderClient?.requestLocationUpdates(
                locationRequest!!,
                locationCallback!!,
                Looper.getMainLooper()
            )
        }
    }
}
