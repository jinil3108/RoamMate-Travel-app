package com.techholding.android.roammate.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import java.util.*

object LocationManager {

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var userLocation: Location? = null

    fun findDistanceFromUserLocation(
        destinationLatitude: Double,
        destinationLongitude: Double
    ): Double? {
        if (userLocation == null) {
            return null
        }
        val startPoint = Location("MyLocation")
        startPoint.latitude = userLocation!!.latitude
        startPoint.longitude = userLocation!!.longitude

        val endPoint = Location("EndLocation")
        endPoint.latitude = destinationLatitude
        endPoint.longitude = destinationLongitude

        return startPoint.distanceTo(endPoint).toDouble() / 1000
    }

    fun setProviderClient(activity: Activity) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    }

    @SuppressLint("MissingPermission")
    fun fetchLiveLocation(
        activity: Activity,
        onSuccess: (String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        mFusedLocationClient.lastLocation.addOnSuccessListener { lastLocation ->
            if (lastLocation != null) {
                userLocation = lastLocation
                val geocoder = Geocoder(activity, Locale.getDefault())
                val addresses =
                    geocoder.getFromLocation(lastLocation.latitude, lastLocation.longitude, 1)
                addresses?.get(0)?.let {
                    onSuccess.invoke(it.locality)
                }
            } else {
                mFusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    object : CancellationToken() {
                        override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
                            onError.invoke(Exception("Cancel Request"))
                            return CancellationTokenSource().token
                        }

                        override fun isCancellationRequested(): Boolean {
                            return false
                        }

                    }
                ).addOnSuccessListener { currentLocation ->
                    userLocation = currentLocation
                    val geocoder = Geocoder(activity, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(
                        currentLocation.latitude,
                        currentLocation.longitude,
                        1
                    )
                    addresses?.get(0)?.let {
                        onSuccess.invoke(it.locality)
                    }
                }.addOnFailureListener { exception ->
                    onError.invoke(exception)
                }
            }
        }
    }

    fun getUserLocation(): Location? = userLocation

}