package com.example.data.location

interface UserLocationManager {

    suspend fun isGpsEnabled(): Boolean
}
