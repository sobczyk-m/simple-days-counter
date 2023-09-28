package com.example.simpledayscounter.ui

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.simpledayscounter.AddCountdownActivity
import com.example.simpledayscounter.CounterApplication
import com.example.simpledayscounter.data.enumeration.CountingType
import com.example.simpledayscounter.data.model.Counter
import com.example.simpledayscounter.data.repository.CounterRepository
import com.example.simpledayscounter.utils.DateCalculationUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

private const val TAG = "HomeViewModel"

class HomeViewModel(private val counterRepository: CounterRepository) : ViewModel() {

    private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    init {
        createCountersFromDatabase()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CounterApplication)
                val counterRepository = application.container.counterRepository
                HomeViewModel(counterRepository = counterRepository)
            }
        }
    }

    private fun createCountersFromDatabase() {

        viewModelScope.launch {
            counterRepository.getWholeListFromCounter()
                .catch { exception -> exception.message?.let { Log.e(TAG, it) } }
                .collect { counters ->
                    val counterUiList = counters.map { counter -> mapCounters(counter) }
                    _homeUiState.value = HomeUiState(counterUiList)
                }
        }
    }

    private fun mapCounters(counter: Counter): CounterUiState {
        val counterId: Int? = counter.counterId
        val eventName = counter.eventName
        val bgStartColor: Int = counter.bgStartColor
        val bgCenterColor: Int = counter.bgCenterColor
        val bgEndColor: Int = counter.bgEndColor
        val countingType: CountingType = counter.countingType
        val dayOfMonth: Int = counter.dayOfMonth
        val month: Int = counter.month
        val year: Int = counter.year
        val includeMonday: Boolean = counter.includeMonday
        val includeTuesday: Boolean = counter.includeTuesday
        val includeWednesday: Boolean = counter.includeWednesday
        val includeThursday: Boolean = counter.includeThursday
        val includeFriday: Boolean = counter.includeFriday
        val includeSaturday: Boolean = counter.includeSaturday
        val includeSunday: Boolean = counter.includeSunday

        var countingNumber: String
        var countingDirection: CountingDirection


        val sdfSelectedDate = DateCalculationUtils(year, month, dayOfMonth).sdfSelectedDate
        val sdfCurrentDate = DateCalculationUtils(year, month, dayOfMonth).sdfCurrentDate

        val daysOfWeekRemaining = DateCalculationUtils(year, month, dayOfMonth).countDaysOfWeek(
            sdfCurrentDate, sdfSelectedDate
        )

        val differenceInDays = DateCalculationUtils(year, month, dayOfMonth).differenceInDays()
        val differenceInYears = DateCalculationUtils(year, month, dayOfMonth).differenceInYears()
        val differenceInYearsFraction =
            DateCalculationUtils(year, month, dayOfMonth).differenceInYearsFraction()

        when (countingType) {
            CountingType.DAYS -> {
                countingNumber =
                    (DateCalculationUtils(year, month, dayOfMonth)).excludeDayOfWeek(
                        differenceInDays,
                        daysOfWeekRemaining,
                        includeMonday,
                        includeTuesday,
                        includeWednesday,
                        includeThursday,
                        includeFriday,
                        includeSaturday,
                        includeSunday
                    ).toString()
            }

            CountingType.WEEKS -> {
                val differenceInWeeks =
                    DateCalculationUtils(year, month, dayOfMonth).differenceInWeeksInt()
                val differenceInWeeksFraction =
                    DateCalculationUtils(year, month, dayOfMonth).differenceInWeeksFraction()

                countingNumber = "$differenceInWeeks.$differenceInWeeksFraction"
            }

            CountingType.MONTHS -> {
                val differenceInMonths =
                    DateCalculationUtils(year, month, dayOfMonth).differenceInMonths()
                val differenceInMonthsFraction =
                    DateCalculationUtils(year, month, dayOfMonth).differenceInMonthsFraction()
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

        return CounterUiState(
            counterId = counterId,
            eventName = eventName,
            countingNumber = countingNumber,
            countingType = countingType,
            countingDirection = countingDirection,
            bgStartColor = bgStartColor,
            bgCenterColor = bgCenterColor,
            bgEndColor = bgEndColor
        )
    }

    public fun addCountdown(context: Context) {
        val intent = Intent(context, AddCountdownActivity::class.java)
        context.startActivity(intent)
    }
}

data class HomeUiState(val counterList: List<CounterUiState> = listOf())
