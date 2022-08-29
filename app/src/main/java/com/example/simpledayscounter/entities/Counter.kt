package com.example.simpledayscounter.entities
import android.graphics.drawable.GradientDrawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity
data class Counter(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val bgStartColor: Int,
    val bgCenterColor: Int,
    val bgEndColor: Int,
    val selectedDate: String,
    val eventName: String,
    val countingText: String,
    val countingNumber: String,
)