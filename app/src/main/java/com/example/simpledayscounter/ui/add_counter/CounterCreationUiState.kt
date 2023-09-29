package com.example.simpledayscounter.ui.add_counter

import com.example.simpledayscounter.data.enumeration.CountingType
import com.example.simpledayscounter.data.model.Counter

data class CounterCreationUiState(
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