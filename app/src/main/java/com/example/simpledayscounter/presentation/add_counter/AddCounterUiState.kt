package com.example.simpledayscounter.presentation.add_counter

import com.example.simpledayscounter.data.enumeration.CountingType
import com.example.simpledayscounter.presentation.counters.constants.CountingDirection

data class AddCounterUiState(
    val counterId: Int?,
    val eventName: String,
    val countingNumber: String,
    val bgStartColor: Int,
    val bgCenterColor: Int,
    val bgEndColor: Int,
    val countingType: CountingType,
    val countingDirection: CountingDirection,
    val dayOfMonth: Int,
    val month: Int,
    val year: Int,
    val includeMonday: Boolean,
    val includeTuesday: Boolean,
    val includeWednesday: Boolean,
    val includeThursday: Boolean,
    val includeFriday: Boolean,
    val includeSaturday: Boolean,
    val includeSunday: Boolean,
)