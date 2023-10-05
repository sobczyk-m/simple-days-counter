package com.example.simpledayscounter.data.repository

import com.example.simpledayscounter.data.data_source.CounterDao
import com.example.simpledayscounter.data.model.Counter
import kotlinx.coroutines.flow.Flow

class CounterRepositoryImpl(
    private val dao: CounterDao
) : CounterRepository {
    override suspend fun insertCounter(counter: Counter) {
        dao.insertCounter(counter)
    }

    override suspend fun deleteCounter(counterId: Int) {
        dao.deleteCounter(counterId)
    }

    override fun getWholeListFromCounter(): Flow<List<Counter>> {
        return dao.getWholeListFromCounter()
    }
}