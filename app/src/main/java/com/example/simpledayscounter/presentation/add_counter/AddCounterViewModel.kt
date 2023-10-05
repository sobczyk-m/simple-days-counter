package com.example.simpledayscounter.presentation.add_counter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.simpledayscounter.SimpleDaysCounterApplication
import com.example.simpledayscounter.data.enumeration.CountingType
import com.example.simpledayscounter.data.model.Counter
import com.example.simpledayscounter.data.repository.CounterRepository
import com.example.simpledayscounter.presentation.add_counter.constants.CounterColor
import com.example.simpledayscounter.presentation.add_counter.constants.DaysOfWeek
import com.example.simpledayscounter.presentation.counters.constants.CountingDirection
import com.example.simpledayscounter.utils.DateCalculationUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

private const val TAG = "CounterCreationViewModel"

class AddCounterViewModel(private val counterRepository: CounterRepository) : ViewModel() {

    private val _AddCounterUiState = MutableStateFlow(
        AddCounterUiState(
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

    val addCounterState: StateFlow<AddCounterUiState> = _AddCounterUiState.asStateFlow()

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SimpleDaysCounterApplication)
                val counterRepository = application.container.counterRepository
                AddCounterViewModel(counterRepository = counterRepository)
            }
        }
    }

    fun changeEventName(name: String) {
        _AddCounterUiState.value = _AddCounterUiState.value
            .copy(eventName = name)
    }

    enum class Colors {
        StartColor, MiddleColor, EndColor
    }

    fun changeCounterColor(color: Colors, picked: Int = 0) {
        when (color) {
            Colors.StartColor -> _AddCounterUiState.value =
                _AddCounterUiState.value.copy(bgStartColor = picked)

            Colors.MiddleColor -> _AddCounterUiState.value =
                _AddCounterUiState.value.copy(bgCenterColor = picked)

            Colors.EndColor -> _AddCounterUiState.value =
                _AddCounterUiState.value.copy(bgEndColor = picked)
        }
    }

    fun changeCounterColor(colorToChange: CounterColor, color: Int = 0) {
        when (colorToChange) {
            CounterColor.StartColor -> _AddCounterUiState.value =
                _AddCounterUiState.value.copy(bgStartColor = color)

            CounterColor.CenterColor -> _AddCounterUiState.value =
                _AddCounterUiState.value.copy(bgCenterColor = color)

            CounterColor.EndColor -> _AddCounterUiState.value =
                _AddCounterUiState.value.copy(bgEndColor = color)
        }

    }

    fun toggleDayOfWeek(dayOfWeek: DaysOfWeek) {
        when (dayOfWeek) {
            DaysOfWeek.Monday -> _AddCounterUiState.value = _AddCounterUiState.value
                .copy(includeMonday = !_AddCounterUiState.value.includeMonday)

            DaysOfWeek.Tuesday -> _AddCounterUiState.value = _AddCounterUiState.value
                .copy(includeTuesday = !_AddCounterUiState.value.includeTuesday)

            DaysOfWeek.Wednesday -> _AddCounterUiState.value = _AddCounterUiState.value
                .copy(includeWednesday = !_AddCounterUiState.value.includeWednesday)

            DaysOfWeek.Thursday -> _AddCounterUiState.value = _AddCounterUiState.value
                .copy(includeThursday = !_AddCounterUiState.value.includeThursday)

            DaysOfWeek.Friday -> _AddCounterUiState.value = _AddCounterUiState.value
                .copy(includeFriday = !_AddCounterUiState.value.includeFriday)

            DaysOfWeek.Saturday -> _AddCounterUiState.value = _AddCounterUiState.value
                .copy(includeSaturday = !_AddCounterUiState.value.includeSaturday)

            DaysOfWeek.Sunday -> _AddCounterUiState.value = _AddCounterUiState.value
                .copy(includeSunday = !_AddCounterUiState.value.includeSunday)
        }
    }

    fun changeCountingType(countingType: CountingType) {
        when (countingType) {
            CountingType.DAYS -> _AddCounterUiState.value = _AddCounterUiState.value
                .copy(countingType = CountingType.DAYS)

            CountingType.WEEKS -> _AddCounterUiState.value = _AddCounterUiState.value
                .copy(countingType = CountingType.WEEKS)

            CountingType.MONTHS -> _AddCounterUiState.value = _AddCounterUiState.value
                .copy(countingType = CountingType.MONTHS)

            CountingType.YEARS -> _AddCounterUiState.value = _AddCounterUiState.value
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

        when (addCounterState.value.countingType) {
            CountingType.DAYS -> {
                countingNumber =
                    (DateCalculationUtils(
                        selectedYear,
                        selectedMonth,
                        selectedDay
                    )).excludeDayOfWeek(
                        differenceInDays,
                        daysOfWeekRemaining,
                        addCounterState.value.includeMonday,
                        addCounterState.value.includeTuesday,
                        addCounterState.value.includeWednesday,
                        addCounterState.value.includeThursday,
                        addCounterState.value.includeFriday,
                        addCounterState.value.includeSaturday,
                        addCounterState.value.includeSunday
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

        _AddCounterUiState.value = _AddCounterUiState.value.copy(
            dayOfMonth = selectedDay,
            month = selectedMonth,
            year = selectedYear,
            countingNumber = countingNumber,
            countingDirection = countingDirection
        )
    }

    fun saveCounter() {
        if (_AddCounterUiState.value.dayOfMonth != 0
            && _AddCounterUiState.value.month != 0
            && _AddCounterUiState.value.year != 0
        ) {
            val counterToSave = Counter(
                counterId = _AddCounterUiState.value.counterId,
                eventName = _AddCounterUiState.value.eventName,
                bgStartColor = _AddCounterUiState.value.bgStartColor,
                bgCenterColor = _AddCounterUiState.value.bgCenterColor,
                bgEndColor = _AddCounterUiState.value.bgEndColor,
                dayOfMonth = _AddCounterUiState.value.dayOfMonth,
                month = _AddCounterUiState.value.month,
                year = _AddCounterUiState.value.year,
                countingType = _AddCounterUiState.value.countingType,
                includeMonday = _AddCounterUiState.value.includeMonday,
                includeTuesday = _AddCounterUiState.value.includeTuesday,
                includeWednesday = _AddCounterUiState.value.includeWednesday,
                includeThursday = _AddCounterUiState.value.includeThursday,
                includeFriday = _AddCounterUiState.value.includeFriday,
                includeSaturday = _AddCounterUiState.value.includeSaturday,
                includeSunday = _AddCounterUiState.value.includeSunday
            )

            viewModelScope.launch() {
                counterRepository.insertCounter(counterToSave)
            }
        }
    }
}
