package com.example.simpledayscounter.presentation.counters

import com.example.simpledayscounter.data.enumeration.CountingType
import com.example.simpledayscounter.presentation.counters.constants.CountingDirection

data class CounterUiState(
    val counterId: Int?,
    val eventName: String,
    val countingNumber: String,
    val countingType: CountingType,
    val countingDirection: CountingDirection,
    val bgStartColor: Int,
    val bgCenterColor: Int,
    val bgEndColor: Int,
)