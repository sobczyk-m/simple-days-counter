package com.example.simpledayscounter.presentation.add_counter

import  androidx.lifecycle.ViewModel
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

    private val _addCounterUiState = MutableStateFlow(
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

    val addCounterState: StateFlow<AddCounterUiState> = _addCounterUiState.asStateFlow()

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SimpleDaysCounterApplication)
                val counterRepository = application.container.counterRepository
                AddCounterViewModel(counterRepository = counterRepository)
            }
        }
    }

    fun changeEventName(name: String) {
        _addCounterUiState.value = _addCounterUiState.value.copy(eventName = name)
    }

    fun changeCounterColor(colorToChange: CounterColor, color: Int = 0) {
        _addCounterUiState.value = when (colorToChange) {
            CounterColor.StartColor -> _addCounterUiState.value.copy(bgStartColor = color)
            CounterColor.CenterColor -> _addCounterUiState.value.copy(bgCenterColor = color)
            CounterColor.EndColor -> _addCounterUiState.value.copy(bgEndColor = color)
        }
    }

    fun toggleDayOfWeek(dayOfWeek: DaysOfWeek) {
        _addCounterUiState.value = when (dayOfWeek) {
            DaysOfWeek.Monday -> _addCounterUiState.value.copy(includeMonday = !_addCounterUiState.value.includeMonday)
            DaysOfWeek.Tuesday -> _addCounterUiState.value.copy(includeTuesday = !_addCounterUiState.value.includeTuesday)
            DaysOfWeek.Wednesday -> _addCounterUiState.value.copy(includeWednesday = !_addCounterUiState.value.includeWednesday)
            DaysOfWeek.Thursday -> _addCounterUiState.value.copy(includeThursday = !_addCounterUiState.value.includeThursday)
            DaysOfWeek.Friday -> _addCounterUiState.value.copy(includeFriday = !_addCounterUiState.value.includeFriday)
            DaysOfWeek.Saturday -> _addCounterUiState.value.copy(includeSaturday = !_addCounterUiState.value.includeSaturday)
            DaysOfWeek.Sunday -> _addCounterUiState.value.copy(includeSunday = !_addCounterUiState.value.includeSunday)
        }
    }

    fun changeCountingType(countingType: CountingType) {
        _addCounterUiState.value = _addCounterUiState.value.copy(countingType = countingType)
    }

    fun handleDatePick(
        selectedDay: Int,
        selectedMonth: Int,
        selectedYear: Int
    ) {
        var countingNumber: String
        val dateCalculationUtils = DateCalculationUtils(selectedYear, selectedMonth, selectedDay)

        val sdfSelectedDate = dateCalculationUtils.sdfSelectedDate
        val sdfCurrentDate = dateCalculationUtils.sdfCurrentDate

        val daysOfWeekRemaining =
            dateCalculationUtils.countDaysOfWeek(sdfCurrentDate, sdfSelectedDate)

        val differenceInDays = dateCalculationUtils.differenceInDays()
        val differenceInYears = dateCalculationUtils.differenceInYears()
        val differenceInYearsFraction = dateCalculationUtils.differenceInYearsFraction()

        countingNumber = when (addCounterState.value.countingType) {
            CountingType.DAYS -> {
                dateCalculationUtils.excludeDayOfWeek(
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
                val differenceInWeeks = dateCalculationUtils.differenceInWeeksInt()
                val differenceInWeeksFraction = dateCalculationUtils.differenceInWeeksFraction()

                "$differenceInWeeks.$differenceInWeeksFraction"
            }

            CountingType.MONTHS -> {
                val differenceInMonths = dateCalculationUtils.differenceInMonths()
                val differenceInMonthsFraction = dateCalculationUtils.differenceInMonthsFraction()
                val sumOfMonths = differenceInMonths + differenceInYears * 12

                "${sumOfMonths.absoluteValue}.${differenceInMonthsFraction}"
            }

            CountingType.YEARS -> {
                "${differenceInYears.absoluteValue}.${differenceInYearsFraction}"
            }
        }

        val countingDirection =
            if (differenceInDays < 0) CountingDirection.PAST else CountingDirection.FUTURE

        _addCounterUiState.value = _addCounterUiState.value.copy(
            dayOfMonth = selectedDay,
            month = selectedMonth,
            year = selectedYear,
            countingNumber = countingNumber,
            countingDirection = countingDirection
        )
    }

    fun saveCounter() {
        if (_addCounterUiState.value.dayOfMonth != 0
            && _addCounterUiState.value.month != 0
            && _addCounterUiState.value.year != 0
        ) {
            val counterToSave = Counter(
                counterId = _addCounterUiState.value.counterId,
                eventName = _addCounterUiState.value.eventName,
                bgStartColor = _addCounterUiState.value.bgStartColor,
                bgCenterColor = _addCounterUiState.value.bgCenterColor,
                bgEndColor = _addCounterUiState.value.bgEndColor,
                dayOfMonth = _addCounterUiState.value.dayOfMonth,
                month = _addCounterUiState.value.month,
                year = _addCounterUiState.value.year,
                countingType = _addCounterUiState.value.countingType,
                includeMonday = _addCounterUiState.value.includeMonday,
                includeTuesday = _addCounterUiState.value.includeTuesday,
                includeWednesday = _addCounterUiState.value.includeWednesday,
                includeThursday = _addCounterUiState.value.includeThursday,
                includeFriday = _addCounterUiState.value.includeFriday,
                includeSaturday = _addCounterUiState.value.includeSaturday,
                includeSunday = _addCounterUiState.value.includeSunday
            )

            viewModelScope.launch() {
                counterRepository.insertCounter(counterToSave)
            }
        }
    }
}
