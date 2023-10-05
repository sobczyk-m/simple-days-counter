package com.example.simpledayscounter.data.repository

import com.example.simpledayscounter.data.model.Counter
import kotlinx.coroutines.flow.Flow

interface CounterRepository {

    suspend fun insertCounter(counter: Counter)

    suspend fun deleteCounter(counterId: Int)

    fun getWholeListFromCounter(): Flow<List<Counter>>
}