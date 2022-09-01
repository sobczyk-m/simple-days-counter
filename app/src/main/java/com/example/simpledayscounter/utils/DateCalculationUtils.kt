package com.example.simpledayscounter.utils

import java.text.SimpleDateFormat
import java.time.LocalDate.now
import java.time.LocalDate.of
import java.time.Period.between
import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

class DateCalculationUtils(mYear: Int, mMonth: Int, private val mDayOfMonth: Int) {

    private val selectedDate = "$mDayOfMonth/${mMonth + 1}/$mYear"
    private val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

    val sdfSelectedDate: Date = sdf.parse(selectedDate) as Date
    val sdfCurrentDate: Date = sdf.parse(sdf.format(System.currentTimeMillis())) as Date

    private val selectedLocaleDate = of(mYear, mMonth + 1, mDayOfMonth)
    private val currentLocaleDate = now()
    private val periodBetween = between(currentLocaleDate, selectedLocaleDate)

    fun differenceInDays(): Int {
        val selectedDateInDays: Long = sdfSelectedDate.time / (1000 * 60 * 60 * 24)
        val currentDateInDays: Long = sdfCurrentDate.time / (1000 * 60 * 60 * 24)

        return (selectedDateInDays - currentDateInDays).toInt()
    }

    private fun differenceInWeeks(): Double {
        val selectedDateInWeeks: Long = sdfSelectedDate.time / (1000 * 60 * 60 * 24)
        val currentDateInWeeks: Long = sdfCurrentDate.time / (1000 * 60 * 60 * 24)

        return (selectedDateInWeeks - currentDateInWeeks).toDouble() / 7
    }

    fun differenceInWeeksInt(): Int {
        return (kotlin.math.floor(differenceInWeeks().absoluteValue)).toInt()
    }

    fun differenceInWeeksFraction(): Int {
        return (((differenceInWeeks().absoluteValue - differenceInWeeksInt())) * 10).roundToInt()
    }

    fun differenceInMonths(): Int {
        return periodBetween.months
    }

    fun differenceInMonthsFraction(): Int {

        var monthFraction = 0
        val differenceInDays = differenceInDays()
        val differenceInMonths = differenceInMonths()
        val differenceInYears = differenceInYears()
        val differenceInDaysOfMonth = differenceInDaysOfMonth()
        val sumOfMonths = differenceInMonths + differenceInYears * 12

        fun changeFromTenToNine(number: Int): Int {
            return when (number) {
                10 -> 9
                else -> number
            }
        }

        fun calculateFraction(x: Double): Int {
            val avgOfDaysInMonths = differenceInDays.toDouble() / sumOfMonths
            val avgOfDayInMonth = (differenceInDaysOfMonth / avgOfDaysInMonths)

            monthFraction = ((x - avgOfDayInMonth) * 10).roundToInt().absoluteValue
            return changeFromTenToNine(monthFraction)
        }

        when {
            sumOfMonths > 1 -> {
                if (differenceInDaysOfMonth > 0) {
                    calculateFraction(1.0)
                } else if (differenceInDaysOfMonth < 0) {
                    calculateFraction(0.0)
                }
            }
            sumOfMonths < -1 -> {
                if (differenceInDaysOfMonth > 0) {
                    calculateFraction(0.0)
                } else if (differenceInDaysOfMonth < 0) {
                    calculateFraction(-1.0)
                }
            }
            sumOfMonths == 1 || sumOfMonths == -1 -> {
                monthFraction =
                    changeFromTenToNine(((((differenceInDays.toDouble() / 31) * 10).absoluteValue) - 10).roundToInt())
            }
            sumOfMonths == 0 -> {
                monthFraction =
                    changeFromTenToNine(((((differenceInDays.toDouble() / 31) * 10).absoluteValue).roundToInt()))
            }
            differenceInDaysOfMonth == 0 -> monthFraction = 0
        }
        return monthFraction
    }

    fun differenceInYears(): Int {
        return periodBetween.years
    }

    fun differenceInYearsFraction(): Int {
        return ((differenceInMonths().toDouble() / 12) * 10).roundToInt().absoluteValue
    }

    fun differenceInDaysOfMonth(): Int {
        return currentLocaleDate.dayOfMonth - mDayOfMonth
    }

    fun countDaysOfWeek(dateStart: Date, dateEnd: Date): Array<Int> {
        val calStart = Calendar.getInstance()
        val calEnd = Calendar.getInstance()

        calStart.time = dateStart
        calEnd.time = dateEnd

        var mondaysNumber = 0
        var tuesdaysNumber = 0
        var wednesdaysNumber = 0
        var thursdaysNumber = 0
        var fridaysNumber = 0
        var saturdaysNumber = 0
        var sundaysNumber = 0

        fun addDaysWhen() {
            when {
                calStart[Calendar.DAY_OF_WEEK] == Calendar.MONDAY -> ++mondaysNumber
                calStart[Calendar.DAY_OF_WEEK] == Calendar.TUESDAY -> ++tuesdaysNumber
                calStart[Calendar.DAY_OF_WEEK] == Calendar.WEDNESDAY -> ++wednesdaysNumber
                calStart[Calendar.DAY_OF_WEEK] == Calendar.THURSDAY -> ++thursdaysNumber
                calStart[Calendar.DAY_OF_WEEK] == Calendar.FRIDAY -> ++fridaysNumber
                calStart[Calendar.DAY_OF_WEEK] == Calendar.SATURDAY -> ++saturdaysNumber
                calStart[Calendar.DAY_OF_WEEK] == Calendar.SUNDAY -> ++sundaysNumber
            }
        }

        if (calStart.time > calEnd.time) {
            calStart.time = dateEnd
            calEnd.time = dateStart
            while (calStart.time < calEnd.time) {
                addDaysWhen()
                calStart.add(Calendar.DAY_OF_MONTH, 1)
            }
        } else {
            while (calStart.time < calEnd.time) {
                calStart.add(Calendar.DAY_OF_MONTH, 1)
                addDaysWhen()
            }
        }

        return arrayOf(
            mondaysNumber,
            tuesdaysNumber,
            wednesdaysNumber,
            thursdaysNumber,
            fridaysNumber,
            saturdaysNumber,
            sundaysNumber
        )
    }

    fun excludeDayOfWeek(
        totalDays: Int,
        // Days of week sorted from Monday to Sunday
        sortedDaysOfWeek: Array<Int>,
        excludeMonday: Boolean,
        excludeTuesday: Boolean,
        excludeWednesday: Boolean,
        excludeThursday: Boolean,
        excludeFriday: Boolean,
        excludeSaturday: Boolean,
        excludeSunday: Boolean
    ): Int {
        var daysAfterExcluding = totalDays.absoluteValue

        if (excludeMonday) daysAfterExcluding -= sortedDaysOfWeek[0]
        if (excludeTuesday) daysAfterExcluding -= sortedDaysOfWeek[1]
        if (excludeWednesday) daysAfterExcluding -= sortedDaysOfWeek[2]
        if (excludeThursday) daysAfterExcluding -= sortedDaysOfWeek[3]
        if (excludeFriday) daysAfterExcluding -= sortedDaysOfWeek[4]
        if (excludeSaturday) daysAfterExcluding -= sortedDaysOfWeek[5]
        if (excludeSunday) daysAfterExcluding -= sortedDaysOfWeek[6]

        return daysAfterExcluding
    }
}