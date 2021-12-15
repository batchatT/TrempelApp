package com.example.trempelapp.data_layer.inMemory

import io.reactivex.Single

interface SharedPreferencesManager {

    fun getUserLoginStatus(): Single<String>
}
