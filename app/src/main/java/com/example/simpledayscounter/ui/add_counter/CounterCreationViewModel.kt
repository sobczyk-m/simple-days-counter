package com.example.simpledayscounter.ui.add_counter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.simpledayscounter.CounterApplication
import com.example.simpledayscounter.data.enumeration.CountingType
import com.example.simpledayscounter.data.model.Counter
import com.example.simpledayscounter.data.repository.CounterRepository
import com.example.simpledayscounter.ui.CountingDirection
import com.example.simpledayscounter.utils.DateCalculationUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

private const val TAG = "CounterCreationViewModel"

class CounterCreationViewModel(private val counterRepository: CounterRepository) : ViewModel() {

    private val _CounterCreationUiState = MutableStateFlow(
        CounterCreationUiState(
            counterId = null,
            eventName = "",
            countingNumber = "0",
            bgStartColor = -3052635,
            bgCenterColor = -7952153,
            bgEndColor = -10486799,
            countingType = CountingType.DAYS,
            countingDirection = CountingDirection.FUTURE,
            dayOfMonth = 0,
            month = 0,
            year = 0,
            includeMonday = true,
            includeTuesday = true,
            includeWednesday = true,
            includeThursday = true,
            includeFriday = true,
            includeSaturday = true,
            includeSunday = true
        )
    )

    val counterState: StateFlow<CounterCreationUiState> = _CounterCreationUiState.asStateFlow()

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CounterApplication)
                val counterRepository = application.container.counterRepository
                CounterCreationViewModel(counterRepository = counterRepository)
            }
        }
    }

    fun changeEventName(name: String) {
        _CounterCreationUiState.value = _CounterCreationUiState.value
            .copy(eventName = name)
    }

    enum class Colors {
        StartColor, MiddleColor, EndColor
    }

    fun changeCounterColor(color: Colors, picked: Int = 0) {
        when (color) {
            Colors.StartColor -> _CounterCreationUiState.value =
                _CounterCreationUiState.value.copy(bgStartColor = picked)

            Colors.MiddleColor -> _CounterCreationUiState.value =
                _CounterCreationUiState.value.copy(bgCenterColor = picked)

            Colors.EndColor -> _CounterCreationUiState.value =
                _CounterCreationUiState.value.copy(bgEndColor = picked)
        }
    }

    fun changeCounterColor(colorToChange: CounterColor, color: Int = 0) {
        when (colorToChange) {
            CounterColor.StartColor -> _CounterCreationUiState.value =
                _CounterCreationUiState.value.copy(bgStartColor = color)

            CounterColor.CenterColor -> _CounterCreationUiState.value =
                _CounterCreationUiState.value.copy(bgCenterColor = color)

            CounterColor.EndColor -> _CounterCreationUiState.value =
                _CounterCreationUiState.value.copy(bgEndColor = color)
        }

    }

    fun toggleDayOfWeek(dayOfWeek: DaysOfWeek) {
        when (dayOfWeek) {
            DaysOfWeek.Monday -> _CounterCreationUiState.value = _CounterCreationUiState.value
                .copy(includeMonday = !_CounterCreationUiState.value.includeMonday)

            DaysOfWeek.Tuesday -> _CounterCreationUiState.value = _CounterCreationUiState.value
                .copy(includeTuesday = !_CounterCreationUiState.value.includeTuesday)

            DaysOfWeek.Wednesday -> _CounterCreationUiState.value = _CounterCreationUiState.value
                .copy(includeWednesday = !_CounterCreationUiState.value.includeWednesday)

            DaysOfWeek.Thursday -> _CounterCreationUiState.value = _CounterCreationUiState.value
                .copy(includeThursday = !_CounterCreationUiState.value.includeThursday)

            DaysOfWeek.Friday -> _CounterCreationUiState.value = _CounterCreationUiState.value
                .copy(includeFriday = !_CounterCreationUiState.value.includeFriday)

            DaysOfWeek.Saturday -> _CounterCreationUiState.value = _CounterCreationUiState.value
                .copy(includeSaturday = !_CounterCreationUiState.value.includeSaturday)

            DaysOfWeek.Sunday -> _CounterCreationUiState.value = _CounterCreationUiState.value
                .copy(includeSunday = !_CounterCreationUiState.value.includeSunday)
        }
    }

    fun changeCountingType(countingType: CountingType) {
        when (countingType) {
            CountingType.DAYS -> _CounterCreationUiState.value = _CounterCreationUiState.value
                .copy(countingType = CountingType.DAYS)

            CountingType.WEEKS -> _CounterCreationUiState.value = _CounterCreationUiState.value
                .copy(countingType = CountingType.WEEKS)

            CountingType.MONTHS -> _CounterCreationUiState.value = _CounterCreationUiState.value
                .copy(countingType = CountingType.MONTHS)

            CountingType.YEARS -> _CounterCreationUiState.value = _CounterCreationUiState.value
                .copy(countingType = CountingType.YEARS)
        }
    }

    fun handleDatePick(
        selectedDay: Int,
        selectedMonth: Int,
        selectedYear: Int
    ) {
        var countingNumber: String
        var countingDirection: CountingDirection

        val sdfSelectedDate =
            DateCalculationUtils(selectedYear, selectedMonth, selectedDay).sdfSelectedDate
        val sdfCurrentDate =
            DateCalculationUtils(selectedYear, selectedMonth, selectedDay).sdfCurrentDate

        val daysOfWeekRemaining =
            DateCalculationUtils(selectedYear, selectedMonth, selectedDay).countDaysOfWeek(
                sdfCurrentDate, sdfSelectedDate
            )

        val differenceInDays =
            DateCalculationUtils(selectedYear, selectedMonth, selectedDay).differenceInDays()
        val differenceInYears =
            DateCalculationUtils(selectedYear, selectedMonth, selectedDay).differenceInYears()
        val differenceInYearsFraction =
            DateCalculationUtils(
                selectedYear,
                selectedMonth,
                selectedDay
            ).differenceInYearsFraction()

        when (counterState.value.countingType) {
            CountingType.DAYS -> {
                countingNumber =
                    (DateCalculationUtils(
                        selectedYear,
                        selectedMonth,
                        selectedDay
                    )).excludeDayOfWeek(
                        differenceInDays,
                        daysOfWeekRemaining,
                        counterState.value.includeMonday,
                        counterState.value.includeTuesday,
                        counterState.value.includeWednesday,
                        counterState.value.includeThursday,
                        counterState.value.includeFriday,
                        counterState.value.includeSaturday,
                        counterState.value.includeSunday
                    ).toString()
            }

            CountingType.WEEKS -> {
                val differenceInWeeks =
                    DateCalculationUtils(
                        selectedYear,
                        selectedMonth,
                        selectedDay
                    ).differenceInWeeksInt()
                val differenceInWeeksFraction =
                    DateCalculationUtils(
                        selectedYear,
                        selectedMonth,
                        selectedDay
                    ).differenceInWeeksFraction()

                countingNumber = "$differenceInWeeks.$differenceInWeeksFraction"
            }

            CountingType.MONTHS -> {
                val differenceInMonths =
                    DateCalculationUtils(
                        selectedYear,
                        selectedMonth,
                        selectedDay
                    ).differenceInMonths()
                val differenceInMonthsFraction =
                    DateCalculationUtils(
                        selectedYear,
                        selectedMonth,
                        selectedDay
                    ).differenceInMonthsFraction()
                val sumOfMonths = differenceInMonths + differenceInYears * 12

                countingNumber =
                    "${sumOfMonths.absoluteValue}.${differenceInMonthsFraction}"
            }

            CountingType.YEARS -> {
                countingNumber =
                    "${differenceInYears.absoluteValue}.${differenceInYearsFraction}"
            }
        }

        countingDirection =
            if (differenceInDays < 0) CountingDirection.PAST else CountingDirection.FUTURE

        _CounterCreationUiState.value = _CounterCreationUiState.value.copy(
            dayOfMonth = selectedDay,
            month = selectedMonth,
            year = selectedYear,
            countingNumber = countingNumber,
            countingDirection = countingDirection
        )
    }

    fun saveCounter() {
        if (_CounterCreationUiState.value.dayOfMonth != 0
            && _CounterCreationUiState.value.month != 0
            && _CounterCreationUiState.value.year != 0
        ) {
            val counterToSave = Counter(
                counterId = _CounterCreationUiState.value.counterId,
                eventName = _CounterCreationUiState.value.eventName,
                bgStartColor = _CounterCreationUiState.value.bgStartColor,
                bgCenterColor = _CounterCreationUiState.value.bgCenterColor,
                bgEndColor = _CounterCreationUiState.value.bgEndColor,
                dayOfMonth = _CounterCreationUiState.value.dayOfMonth,
                month = _CounterCreationUiState.value.month,
                year = _CounterCreationUiState.value.year,
                countingType = _CounterCreationUiState.value.countingType,
                includeMonday = _CounterCreationUiState.value.includeMonday,
                includeTuesday = _CounterCreationUiState.value.includeTuesday,
                includeWednesday = _CounterCreationUiState.value.includeWednesday,
                includeThursday = _CounterCreationUiState.value.includeThursday,
                includeFriday = _CounterCreationUiState.value.includeFriday,
                includeSaturday = _CounterCreationUiState.value.includeSaturday,
                includeSunday = _CounterCreationUiState.value.includeSunday
            )

            viewModelScope.launch() {
                counterRepository.insertCounter(counterToSave)
            }
        }
    }
}
