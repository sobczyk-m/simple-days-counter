package com.example.simpledayscounter.entities
import android.graphics.drawable.GradientDrawable
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface CounterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCounter(counter: Counter)

    @Update
    suspend fun updateCounter(counter: Counter)

    @Delete
    suspend fun deleteCounter(counter: Counter)

    @Transaction
    @Query("SELECT * FROM counter")
    suspend fun getWholeListFromCounter(): List<Counter>

    @Transaction
    @Query("SELECT id FROM counter WHERE id = (SELECT MAX(ID) FROM counter)")
    suspend fun getLastId(): Int

    @Transaction
    @Query("SELECT eventName FROM counter")
    suspend fun getEventNameList(): List<String>

    @Transaction
    @Query("SELECT countingText FROM counter")
    suspend fun getCountingTextList(): List<String>

    @Transaction
    @Query("SELECT countingNumber FROM counter")
    suspend fun getCountingNumberList(): List<String>

    @Transaction
    @Query("SELECT bgStartColor FROM counter")
    suspend fun getBgStartColorList(): List<Int>

    @Transaction
    @Query("SELECT bgCenterColor FROM counter")
    suspend fun getBgCenterColorList(): List<Int>

    @Transaction
    @Query("SELECT bgEndColor FROM counter")
    suspend fun getBgEndColorList(): List<Int>

}
