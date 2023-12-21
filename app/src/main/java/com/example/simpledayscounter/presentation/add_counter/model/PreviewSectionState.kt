package com.example.simpledayscounter.presentation.add_counter.model

import com.example.simpledayscounter.data.enumeration.CountingType
import com.example.simpledayscounter.presentation.counters.constants.CountingDirection

data class PreviewSectionState(
    val eventName: String,
    val countingNumber: String,
    val bgStartColor: Int,
    val bgCenterColor: Int,
    val bgEndColor: Int,
    val countingDirection: CountingDirection,
    val countingType: CountingType
)