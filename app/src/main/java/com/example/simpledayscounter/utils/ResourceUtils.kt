package com.example.simpledayscounter.utils

import com.example.simpledayscounter.R
import com.example.simpledayscounter.data.enumeration.CountingType

public fun getCountingTypeResource(countingType: CountingType): Int {
    return when (countingType) {
        CountingType.DAYS -> R.string.rb_days
        CountingType.WEEKS -> R.string.rb_weeks
        CountingType.MONTHS -> R.string.rb_months
        CountingType.YEARS -> R.string.rb_years
    }
}