package com.example.simpledayscounter

import android.graphics.drawable.GradientDrawable

data class CounterDisplay(
    val eventName: String,
    val countingNumber: String,
    val countingText: String,
    val background: GradientDrawable
)