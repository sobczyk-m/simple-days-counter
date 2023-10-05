package com.example.simpledayscounter

import android.app.Application
import com.example.simpledayscounter.data.AppContainer
import com.example.simpledayscounter.data.AppDataContainer

class SimpleDaysCounterApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}