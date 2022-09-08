package com.example.simpledayscounter.entities
import androidx.room.*
import com.example.simpledayscounter.CountingType

@Entity
data class Counter(
    @PrimaryKey(autoGenerate = true)
    val counterId: Int?,
    val eventName: String,
    val bgStartColor: Int,
    val bgCenterColor: Int,
    val bgEndColor: Int,
    val dayOfMonth: Int,
    val month: Int,
    val year: Int,
    val countingType: CountingType,
    val includeMonday: Boolean,
    val includeTuesday: Boolean,
    val includeWednesday: Boolean,
    val includeThursday: Boolean,
    val includeFriday: Boolean,
    val includeSaturday: Boolean,
    val includeSunday: Boolean,
    )