package com.example.simpledayscounter.data

import android.content.Context
import com.example.simpledayscounter.data.data_source.CounterDatabase
import com.example.simpledayscounter.data.repository.CounterRepository
import com.example.simpledayscounter.data.repository.CounterRepositoryImpl


interface AppContainer {
    val counterRepository: CounterRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val counterRepository: CounterRepository by lazy {
        CounterRepositoryImpl(CounterDatabase.getInstance(context).counterDao)
    }
}