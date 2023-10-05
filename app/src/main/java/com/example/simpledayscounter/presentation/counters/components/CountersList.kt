package com.example.simpledayscounter.presentation.counters.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpledayscounter.R
import com.example.simpledayscounter.data.enumeration.CountingType
import com.example.simpledayscounter.presentation.counters.CounterUiState
import com.example.simpledayscounter.presentation.counters.constants.CountingDirection

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CountersList(
    counterList: List<CounterUiState>, modifier: Modifier = Modifier,
    onCounterSwipe: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(5.dp, 5.dp, 5.dp, 0.dp)
    ) {
        items(items = counterList, key = { counter -> counter.counterId!! }) {
            val counter = it
            val timeUnitToDisplay = when (it.countingType) {
                CountingType.DAYS -> R.string.rb_days
                CountingType.WEEKS -> R.string.rb_weeks
                CountingType.MONTHS -> R.string.rb_months
                CountingType.YEARS -> R.string.rb_years
            }

            val direction = if (it.countingDirection == CountingDirection.PAST) {
                R.string.app_widget_counting_text_time_ago
            } else R.string.app_widget_counting_text_time_left

            val dismissState = rememberDismissState(
                confirmValueChange = {
                    if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                        onCounterSwipe(counter.counterId!!)
                    }
                    true
                }
            )

            SwipeToDismiss(
                state = dismissState,
                modifier = Modifier,
                background = { /* TODO() */ },
                dismissContent = {
                    Counter(
                        modifier = Modifier
                            .height(100.dp)
                            .padding(3.dp),
                        eventName = it.eventName,
                        countingNumber = it.countingNumber,
                        countingText = LocalContext.current.getString(
                            direction, (LocalContext.current.getString(timeUnitToDisplay))
                        ),
                        bgStartColor = it.bgStartColor,
                        bgCenterColor = it.bgCenterColor,
                        bgEndColor = it.bgEndColor
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CunterListPreview() {
    CountersList(
        listOf(
            CounterUiState(
                counterId = 1,
                eventName = "R.string.app_widget_event_name)",
                countingNumber = stringResource(R.string.app_widget_counting_number),
                countingType = CountingType.YEARS,
                countingDirection = CountingDirection.FUTURE,
                bgStartColor = -3052635,
                bgCenterColor = -7952153,
                bgEndColor = -10486799
            ),
            CounterUiState(
                counterId = 2,
                eventName = "R.string.app_widget_event_name)s aas as saasas as as ",
                countingNumber = stringResource(R.string.app_widget_counting_number),
                countingType = CountingType.YEARS,
                countingDirection = CountingDirection.FUTURE,
                bgStartColor = -3052635,
                bgCenterColor = -7952153,
                bgEndColor = -10486799
            ), CounterUiState(
                counterId = 3,
                eventName = LocalContext.current.getString(
                    R.string.app_widget_counting_text_time_left,
                    LocalContext.current.getString(R.string.rb_days)
                ),
                countingNumber = stringResource(R.string.app_widget_counting_number),
                countingType = CountingType.YEARS,
                countingDirection = CountingDirection.FUTURE,
                bgStartColor = -3052635,
                bgCenterColor = -7952153,
                bgEndColor = -10486799
            )
        ),
        onCounterSwipe = {}
    )
}