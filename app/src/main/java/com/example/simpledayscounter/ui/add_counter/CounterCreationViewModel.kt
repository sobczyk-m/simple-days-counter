package com.example.simpledayscounter.ui.add_counter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.simpledayscounter.CounterApplication
import com.example.simpledayscounter.data.enumeration.CountingType
import com.example.simpledayscounter.data.model.Counter
import com.example.simpledayscounter.data.repository.CounterRepository
import com.example.simpledayscounter.ui.CounterUiState
import com.example.simpledayscounter.ui.CountingDirection
import com.example.simpledayscounter.utils.DateCalculationUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import kotlin.math.absoluteValue

private const val TAG = "CounterCreationViewModel"

class CounterCreationViewModel(private val counterRepository: CounterRepository) : ViewModel() {

    private val _CounterCreationUiState = MutableStateFlow(
        CounterCreationUiState(
            counterId = null,
            eventName = "",
            bgStartColor = -3052635,
            bgCenterColor = -7952153,
            bgEndColor = -10486799,
            dayOfMonth = LocalDate.now().dayOfMonth,
            month = LocalDate.now().monthValue,
            year = LocalDate.now().year,
            countingType = CountingType.DAYS,
            includeMonday = true,
            includeTuesday = true,
            includeWednesday = true,
            includeThursday = true,
            includeFriday = true,
            includeSaturday = true,
            includeSunday = true,
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
}
