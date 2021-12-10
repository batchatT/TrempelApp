package com.example.trempelapp

import android.app.Application
import com.example.trempelapp.di.DaggerTrempelAppComponent
import com.example.trempelapp.di.TrempelAppComponent


class TrempelApplication: Application() {

    val trempelApp: TrempelAppComponent = DaggerTrempelAppComponent.create()

}
