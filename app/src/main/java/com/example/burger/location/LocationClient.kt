package com.example.burger.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getlcationUpdates(interval: Long): Flow<Location>

    class LocationExeption(message: String): Exception()
}