package com.harrontech.vilniusobjectrecognizer.android.helpers

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

class LocationHelper(val context: Context, var onLocation: (Location?) -> Unit) {
    var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    fun permissionCheck(function: ()-> Unit){
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        function()
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation(){
        permissionCheck {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    onLocation(location)
                }
        }
    }
}