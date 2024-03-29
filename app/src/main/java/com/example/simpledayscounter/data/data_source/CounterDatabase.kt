package com.example.simpledayscounter.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.simpledayscounter.data.model.Counter

@Database(
    entities = [
        Counter::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CounterDatabase : RoomDatabase() {

    abstract val counterDao: CounterDao

    companion object {
        @Volatile
        private var INSTANCE: CounterDatabase? = null

        fun getInstance(context: Context): CounterDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CounterDatabase::class.java,
                    "counter_db"
                ).fallbackToDestructiveMigration()
                    .build().also {
                    INSTANCE = it
                }
            }
        }
    }

}