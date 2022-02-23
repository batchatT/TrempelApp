package com.example.trempelapp

import android.app.Application

class TrempelApplication : Application() {

    val trempelApp: TrempelAppComponent by lazy {
        DaggerTrempelAppComponent.factory().create(applicationContext)
    }
}
