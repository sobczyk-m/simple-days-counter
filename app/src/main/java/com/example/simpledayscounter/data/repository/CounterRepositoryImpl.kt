package com.example.simpledayscounter.data.repository

import com.example.simpledayscounter.data.data_source.CounterDao
import com.example.simpledayscounter.data.enumeration.CountingType
import com.example.simpledayscounter.data.model.Counter
import kotlinx.coroutines.flow.Flow

class CounterRepositoryImpl(
    private val dao: CounterDao
) : CounterRepository {
    override suspend fun insertCounter(counter: Counter) {
        dao.insertCounter(counter)
    }

    override suspend fun updateCounter(counter: Counter) {
        dao.updateCounter(counter)
    }

    override suspend fun deleteCounter(counter: Counter) {
        dao.deleteCounter(counter)
    }

    override fun getWholeListFromCounter(): Flow<List<Counter>> {
        return dao.getWholeListFromCounter()
    }

    override suspend fun getLastId(): Int {
        return dao.getLastId()
    }

    override suspend fun getEventNameList(): List<String> {
        return dao.getEventNameList()
    }

    override suspend fun getBgStartColorList(): List<Int> {
        return dao.getBgStartColorList()
    }

    override suspend fun getBgCenterColorList(): List<Int> {
        return dao.getBgCenterColorList()
    }

    override suspend fun getBgEndColorList(): List<Int> {
        return dao.getBgEndColorList()
    }

    override suspend fun getDayOfMonthList(): List<Int> {
        return dao.getDayOfMonthList()
    }

    override suspend fun getMonthList(): List<Int> {
        return dao.getMonthList()
    }

    override suspend fun getYearList(): List<Int> {
        return dao.getMonthList()
    }

    override suspend fun getCountingType(): CountingType {
        return dao.getCountingType()
    }

    override suspend fun getIncludeMondayList(): List<Int> {
        return dao.getIncludeMondayList()
    }

    override suspend fun getIncludeTuesdayList(): List<Int> {
        return dao.getIncludeTuesdayList()
    }

    override suspend fun getIncludeWednesdayList(): List<Int> {
        return dao.getIncludeWednesdayList()
    }

    override suspend fun getIncludeThursdayList(): List<Int> {
        return dao.getIncludeThursdayList()
    }

    override suspend fun getIncludeFridayList(): List<Int> {
        return dao.getIncludeFridayList()
    }

    override suspend fun getIncludeSaturdayList(): List<Int> {
        return dao.getIncludeSaturdayList()
    }

    override suspend fun getIncludeSundayList(): List<Int> {
        return dao.getIncludeSundayList()
    }
}