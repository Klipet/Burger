package com.example.burger.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class DefaultLocationclient(
    private val context: Context,
    private val client: FusedLocationProviderClient
): LocationClient {

    @SuppressLint("ServiceCast", "MissingPermission")
    override fun getlcationUpdates(interval: Long): Flow<Location> {
        return callbackFlow {
            if(!context.hasLocationPermision()){
                throw LocationClient.LocationExeption("Missing location Permision!")
            }
            val locationMenager = context.getSystemService(Context.LOCALE_SERVICE) as LocationManager
            val isGpsEnable = locationMenager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnable = locationMenager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!isGpsEnable && !isNetworkEnable){
                throw LocationClient.LocationExeption("GPS is Desible")
            }
            val request = com.google.android.gms.location.LocationRequest.create()
                .setInterval(interval)
                .setFastestInterval(interval)

            val locationCallback = object : LocationCallback(){
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    result.locations.lastOrNull()?.let { location ->
                        launch { send(location) }
                    }
                }
            }


            client.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            )

        }

    }
}