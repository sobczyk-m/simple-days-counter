package com.example.simpledayscounter.data.repository

import com.example.simpledayscounter.data.enumeration.CountingType
import com.example.simpledayscounter.data.model.Counter
import kotlinx.coroutines.flow.Flow

interface CounterRepository {

    suspend fun insertCounter(counter: Counter)

    suspend fun updateCounter(counter: Counter)

    suspend fun deleteCounter(counter: Counter)

    fun getWholeListFromCounter(): Flow<List<Counter>>

    suspend fun getLastId(): Int

    suspend fun getEventNameList(): List<String>

    suspend fun getBgStartColorList(): List<Int>

    suspend fun getBgCenterColorList(): List<Int>

    suspend fun getBgEndColorList(): List<Int>

    suspend fun getDayOfMonthList(): List<Int>

    suspend fun getMonthList(): List<Int>

    suspend fun getYearList(): List<Int>

    suspend fun getCountingType(): CountingType

    suspend fun getIncludeMondayList(): List<Int>

    suspend fun getIncludeTuesdayList(): List<Int>

    suspend fun getIncludeWednesdayList(): List<Int>

    suspend fun getIncludeThursdayList(): List<Int>

    suspend fun getIncludeFridayList(): List<Int>

    suspend fun getIncludeSaturdayList(): List<Int>

    suspend fun getIncludeSundayList(): List<Int>

}