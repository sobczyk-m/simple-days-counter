package com.example.simpledayscounter.data.data_source

import androidx.room.*
import com.example.simpledayscounter.data.enumeration.CountingType
import com.example.simpledayscounter.data.model.Counter
import kotlinx.coroutines.flow.Flow

@Dao
interface CounterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCounter(counter: Counter)

    @Update
    suspend fun updateCounter(counter: Counter)

    @Query("DELETE FROM counter WHERE counterId = :counterId")
    suspend fun deleteCounter(counterId: Int)

    @Query("SELECT * FROM counter")
    fun getWholeListFromCounter(): Flow<List<Counter>>

    @Query("SELECT counterId FROM counter WHERE counterId = (SELECT MAX(counterId) FROM counter)")
    suspend fun getLastId(): Int

    @Query("SELECT eventName FROM counter")
    suspend fun getEventNameList(): List<String>

    @Query("SELECT bgStartColor FROM counter")
    suspend fun getBgStartColorList(): List<Int>

    @Query("SELECT bgCenterColor FROM counter")
    suspend fun getBgCenterColorList(): List<Int>

    @Query("SELECT bgEndColor FROM counter")
    suspend fun getBgEndColorList(): List<Int>

    @Query("SELECT dayOfMonth FROM counter")
    suspend fun getDayOfMonthList(): List<Int>

    @Query("SELECT month FROM counter")
    suspend fun getMonthList(): List<Int>

    @Query("SELECT year FROM counter")
    suspend fun getYearList(): List<Int>

    @Query("SELECT countingType FROM counter")
    suspend fun getCountingType(): CountingType

    @Query("SELECT includeMonday FROM counter")
    suspend fun getIncludeMondayList(): List<Int>

    @Query("SELECT includeTuesday FROM counter")
    suspend fun getIncludeTuesdayList(): List<Int>

    @Query("SELECT includeWednesday FROM counter")
    suspend fun getIncludeWednesdayList(): List<Int>

    @Query("SELECT includeThursday FROM counter")
    suspend fun getIncludeThursdayList(): List<Int>

    @Query("SELECT includeFriday FROM counter")
    suspend fun getIncludeFridayList(): List<Int>

    @Query("SELECT includeSaturday FROM counter")
    suspend fun getIncludeSaturdayList(): List<Int>

    @Query("SELECT includeSunday FROM counter")
    suspend fun getIncludeSundayList(): List<Int>
}