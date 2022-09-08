package com.example.simpledayscounter.entities

import androidx.room.*
import com.example.simpledayscounter.CountingType

@Dao
interface CounterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCounter(counter: Counter)

    @Update
    suspend fun updateCounter(counter: Counter)

    @Delete
    suspend fun deleteCounter(counter: Counter)

    @Query("SELECT * FROM counter")
    suspend fun getWholeListFromCounter(): List<Counter>

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
    suspend fun getCountingTypeList(): List<CountingType>

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