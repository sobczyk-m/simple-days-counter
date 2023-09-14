package com.example.simpledayscounter.ui

import com.example.simpledayscounter.data.enumeration.CountingType

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