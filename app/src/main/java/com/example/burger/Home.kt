package com.example.burger

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.media.audiofx.Equalizer.Settings
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class Home : AppCompatActivity() {
    private lateinit var locationText : TextView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationText = findViewById(R.id.tvLocationName)


    }

    fun buttonLocation(view: View) {
       getCurrentLocation()
    }
    fun getCurrentLocation(){

        if (checkPermision()){
            if (isLocationEnabl()){

            }
            else
            {
                Toast.makeText(this, "Turn of Location", Toast.LENGTH_SHORT).show()
 //               val intent = Intent(Settings.)

            }

        }
        else
        {
            requestPermissions()

        }

    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf( android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_ACCESS_LOCATION)
    }

    fun checkPermision(): Boolean {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            true
        }
        return false

    }
    fun isLocationEnabl(): Boolean {
        val locationMenager : LocationManager = getSystemService(Context.LOCALE_SERVICE) as LocationManager
        return locationMenager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationMenager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )


    }
    companion object{
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext, "Garande", Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            }
            else{
                Toast.makeText(applicationContext, "Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

}