package com.example.simpledayscounter.ui.add_counter

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simpledayscounter.R
import com.example.simpledayscounter.data.enumeration.CountingType
import com.example.simpledayscounter.ui.Counter

@Composable
fun CounterCreationScreen(
    modifier: Modifier = Modifier,
    viewModel: CounterCreationViewModel = viewModel(factory = CounterCreationViewModel.Factory)
) {
    val counterState = viewModel.counterState.collectAsState().value
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .padding(10.dp)
            .verticalScroll(scrollState)
    ) {
        Row() {
            Spacer(modifier = Modifier.weight(1f))
            Counter(
                modifier = Modifier.weight(8f),
                eventName = if (counterState.eventName.isEmpty())
                    stringResource(id = R.string.app_widget_event_name) else counterState.eventName
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Column(
            modifier = Modifier
                .padding(0.dp, 20.dp, 0.dp, 0.dp)
        ) {
            repeat(3) { i ->
                Row(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 10.dp)
                ) {
                    Text(text = stringResource(id = R.string.tv_color_pick, i + 1))
                    Spacer(modifier = Modifier.width(5.dp))
                    Box(
                        modifier = Modifier
                            .size(15.dp, 15.dp)
                            .border(2.dp, Color.DarkGray, RoundedCornerShape(2.dp))
                    ) {
                        Button(
                            modifier = Modifier,
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(
                                Color(
                                    when (i) {
                                        0 -> counterState.bgStartColor
                                        1 -> counterState.bgCenterColor
                                        2 -> counterState.bgEndColor
                                        else -> counterState.bgStartColor
                                    }
                                )
                            )
                        ) {}
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(30.dp))
        Text(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 10.dp),
            text = stringResource(id = R.string.tv_countdown_title)
        )
        TextField(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 10.dp),
            placeholder = { Text(text = stringResource(id = R.string.et_countdown_title)) },
            value = counterState.eventName,
            onValueChange = { viewModel.changeEventName(it) }
        )
        Text(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 10.dp),
            text = stringResource(id = R.string.tv_countdown_date)
        )
        TextField(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 10.dp),
            placeholder = { Text(text = stringResource(id = R.string.tv_countdown_date)) },
            value = stringResource(id = R.string.tv_countdown_date),
            onValueChange = {/*TODO*/ })
        Text(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 10.dp),
            text = stringResource(id = R.string.tv_counting_method)
        )
        Row(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CountingType.values().forEachIndexed() { index, countingOption ->
                RadioButton(selected = when (index) {
                    0 -> counterState.countingType == CountingType.DAYS
                    1 -> counterState.countingType == CountingType.WEEKS
                    2 -> counterState.countingType == CountingType.MONTHS
                    3 -> counterState.countingType == CountingType.YEARS
                    else -> false
                }, onClick = {
                    when (countingOption) {
                        CountingType.DAYS -> viewModel.changeCountingType(CountingType.DAYS)
                        CountingType.WEEKS -> viewModel.changeCountingType(CountingType.WEEKS)
                        CountingType.MONTHS -> viewModel.changeCountingType(CountingType.MONTHS)
                        CountingType.YEARS -> viewModel.changeCountingType(CountingType.YEARS)
                    }
                })
                Text(
                    text = stringResource(
                        id = when (countingOption) {
                            CountingType.DAYS -> R.string.rb_days
                            CountingType.WEEKS -> R.string.rb_weeks
                            CountingType.MONTHS -> R.string.rb_months
                            CountingType.YEARS -> R.string.rb_years
                        }
                    )
                )
            }
        }
        Text(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 10.dp),
            text = stringResource(id = R.string.tv_day_exclude)
        )
        Column(modifier = Modifier) {
            DaysOfWeek.values().forEachIndexed { index, dayOfWeek ->
                Row(
                    modifier = modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = when (index) {
                        0 -> counterState.includeMonday
                        1 -> counterState.includeTuesday
                        2 -> counterState.includeWednesday
                        3 -> counterState.includeThursday
                        4 -> counterState.includeFriday
                        5 -> counterState.includeSaturday
                        6 -> counterState.includeSunday
                        else -> false
                    },
                        onCheckedChange = {
                            when (index) {
                                0 -> viewModel.toggleDayOfWeek(DaysOfWeek.Monday)
                                1 -> viewModel.toggleDayOfWeek(DaysOfWeek.Tuesday)
                                2 -> viewModel.toggleDayOfWeek(DaysOfWeek.Wednesday)
                                3 -> viewModel.toggleDayOfWeek(DaysOfWeek.Thursday)
                                4 -> viewModel.toggleDayOfWeek(DaysOfWeek.Friday)
                                5 -> viewModel.toggleDayOfWeek(DaysOfWeek.Saturday)
                                6 -> viewModel.toggleDayOfWeek(DaysOfWeek.Sunday)
                            }
                        })
                    Text(
                        text = stringResource(
                            id = when (dayOfWeek) {
                                DaysOfWeek.Monday -> R.string.first_day_of_week
                                DaysOfWeek.Tuesday -> R.string.second_day_of_week
                                DaysOfWeek.Wednesday -> R.string.third_day_of_week
                                DaysOfWeek.Thursday -> R.string.fourth_day_of_week
                                DaysOfWeek.Friday -> R.string.fifth_day_of_week
                                DaysOfWeek.Saturday -> R.string.sixth_day_of_week
                                DaysOfWeek.Sunday -> R.string.seventh_day_of_week
                            }
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CounterCreationScreenPreview() {
    CounterCreationScreen()
}