package com.example.simpledayscounter.data.data_source

import androidx.room.*
import com.example.simpledayscounter.data.model.Counter
import kotlinx.coroutines.flow.Flow

@Dao
interface CounterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCounter(counter: Counter)

    @Query("DELETE FROM counter WHERE counterId = :counterId")
    suspend fun deleteCounter(counterId: Int)

    @Query("SELECT * FROM counter")
    fun getWholeListFromCounter(): Flow<List<Counter>>
}