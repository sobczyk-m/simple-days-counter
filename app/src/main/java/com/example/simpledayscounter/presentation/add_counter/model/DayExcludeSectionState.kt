package com.example.simpledayscounter.presentation.add_counter.model

data class DayExcludeSectionState(
    val includeMonday: Boolean,
    val includeTuesday: Boolean,
    val includeWednesday: Boolean,
    val includeThursday: Boolean,
    val includeFriday: Boolean,
    val includeSaturday: Boolean,
    val includeSunday: Boolean
)
