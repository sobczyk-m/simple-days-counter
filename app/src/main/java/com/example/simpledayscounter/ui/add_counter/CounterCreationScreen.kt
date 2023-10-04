package com.example.simpledayscounter.ui.add_counter

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.simpledayscounter.R
import com.example.simpledayscounter.data.enumeration.CountingType
import com.example.simpledayscounter.ui.Counter
import com.example.simpledayscounter.ui.CountingDirection
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterCreationScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: CounterCreationViewModel = viewModel(factory = CounterCreationViewModel.Factory)
) {
    val counterState = viewModel.counterState.collectAsState().value
    val scrollState = rememberScrollState()
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    var showColorPicker by remember {
        mutableStateOf(false)
    }
    var colorPickerColorToChange by remember {
        mutableStateOf(CounterColor.StartColor)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (showColorPicker) Text(stringResource(R.string.choose_color)) else
                        Text(stringResource(R.string.creation))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = modifier,
                navigationIcon = {
                    if (!showColorPicker) {
                        IconButton(
                            onClick = {
                                if (navController.previousBackStackEntry != null) navController.navigateUp()
                            }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.btn_back)
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (showColorPicker) {
                            showColorPicker = false
                        } else {
                            if (counterState.dayOfMonth != 0) {
                                viewModel.saveCounter()
                                navController.navigateUp()
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = stringResource(R.string.btn_save)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(10.dp)
                .verticalScroll(scrollState)
        ) {
            if (!showColorPicker) {
                Row() {
                    Spacer(modifier = Modifier.weight(1f))
                    Counter(
                        modifier = Modifier.weight(8f),
                        eventName = if (counterState.eventName.isEmpty())
                            stringResource(id = R.string.app_widget_event_name) else counterState.eventName,
                        countingNumber = when (counterState.countingDirection) {
                            CountingDirection.PAST -> "-${counterState.countingNumber}"
                            CountingDirection.FUTURE -> counterState.countingNumber
                        },
                        countingText = when (counterState.countingDirection) {
                            CountingDirection.PAST -> stringResource(
                                id = R.string.app_widget_counting_text_time_ago,
                                stringResource(id = getCountingTypeResource(counterState.countingType))
                            )

                            CountingDirection.FUTURE -> stringResource(
                                id = R.string.app_widget_counting_text_time_left,
                                stringResource(id = getCountingTypeResource(counterState.countingType))
                            )
                        },
                        bgStartColor = counterState.bgStartColor,
                        bgCenterColor = counterState.bgCenterColor,
                        bgEndColor = counterState.bgEndColor,
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
                                    onClick = {
                                        showColorPicker = true
                                        colorPickerColorToChange = when (i) {
                                            0 -> CounterColor.StartColor
                                            1 -> CounterColor.CenterColor
                                            2 -> CounterColor.EndColor
                                            else -> CounterColor.StartColor
                                        }
                                    },
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
                    colors = TextFieldDefaults.colors(disabledPlaceholderColor = Color.Black),
                    onValueChange = { viewModel.changeEventName(it) }
                )
                Text(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 10.dp),
                    text = stringResource(id = R.string.tv_countdown_date)
                )
                TextField(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 10.dp)
                        .clickable { showDatePicker = !showDatePicker },
                    placeholder = { Text(text = stringResource(id = R.string.et_select_event_date)) },
                    enabled = false,
                    value = if (counterState.dayOfMonth == 0) "" else "${counterState.dayOfMonth}/${counterState.month}/${counterState.year}",
                    onValueChange = {},
                    colors = TextFieldDefaults.colors(
                        disabledPlaceholderColor = Color.Black,
                        disabledTextColor = Color.Black
                    )
                )

                if (showDatePicker) {
                    val snackState = remember { SnackbarHostState() }
                    val dpdScope = rememberCoroutineScope()
                    SnackbarHost(hostState = snackState, Modifier)
                    val openDialog = remember { mutableStateOf(true) }
                    if (openDialog.value) {
                        val datePickerState = rememberDatePickerState()
                        val confirmEnabled =
                            derivedStateOf { datePickerState.selectedDateMillis != null }
                        DatePickerDialog(
                            onDismissRequest = {
                                // Dismiss the dialog when the user clicks outside the dialog or on the back button.
                                openDialog.value = false
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        openDialog.value = false
                                        dpdScope.launch {
                                            val selectedTimeStamp: Long =
                                                datePickerState.selectedDateMillis!!
                                            val cal = Calendar.getInstance()
                                            cal.timeInMillis = selectedTimeStamp

                                            val selectedDay = cal[Calendar.DAY_OF_MONTH]
                                            val selectedMonth = cal[Calendar.MONTH]
                                            val selectedYear = cal[Calendar.YEAR]

                                            viewModel.handleDatePick(
                                                selectedDay,
                                                selectedMonth,
                                                selectedYear
                                            )
                                        }
                                    },
                                    enabled = confirmEnabled.value
                                ) {
                                    Text("OK")
                                }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = {
                                        openDialog.value = false
                                    }
                                ) {
                                    Text("Cancel")
                                }
                            }
                        ) {
                            DatePicker(state = datePickerState)
                        }
                    }
                }

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
                                CountingType.DAYS -> {
                                    viewModel.changeCountingType(CountingType.DAYS)
                                    if (counterState.dayOfMonth != 0)
                                        viewModel.handleDatePick(
                                            counterState.dayOfMonth,
                                            counterState.month,
                                            counterState.year
                                        )
                                }

                                CountingType.WEEKS -> {
                                    viewModel.changeCountingType(CountingType.WEEKS)
                                    if (counterState.dayOfMonth != 0)
                                        viewModel.handleDatePick(
                                            counterState.dayOfMonth,
                                            counterState.month,
                                            counterState.year
                                        )
                                }

                                CountingType.MONTHS -> {
                                    viewModel.changeCountingType(CountingType.MONTHS)
                                    if (counterState.dayOfMonth != 0)
                                        viewModel.handleDatePick(
                                            counterState.dayOfMonth,
                                            counterState.month,
                                            counterState.year
                                        )
                                }

                                CountingType.YEARS -> {
                                    viewModel.changeCountingType(CountingType.YEARS)
                                    if (counterState.dayOfMonth != 0)
                                        viewModel.handleDatePick(
                                            counterState.dayOfMonth,
                                            counterState.month,
                                            counterState.year
                                        )
                                }
                            }
                        })
                        Text(
                            text = stringResource(
                                id = getCountingTypeResource(countingOption)
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
                                        0 -> {
                                            viewModel.toggleDayOfWeek(DaysOfWeek.Monday)
                                            if (counterState.dayOfMonth != 0)
                                                viewModel.handleDatePick(
                                                    counterState.dayOfMonth,
                                                    counterState.month,
                                                    counterState.year
                                                )
                                        }

                                        1 -> {
                                            viewModel.toggleDayOfWeek(DaysOfWeek.Tuesday)
                                            if (counterState.dayOfMonth != 0)
                                                viewModel.handleDatePick(
                                                    counterState.dayOfMonth,
                                                    counterState.month,
                                                    counterState.year
                                                )
                                        }

                                        2 -> {
                                            viewModel.toggleDayOfWeek(DaysOfWeek.Wednesday)
                                            if (counterState.dayOfMonth != 0)
                                                viewModel.handleDatePick(
                                                    counterState.dayOfMonth,
                                                    counterState.month,
                                                    counterState.year
                                                )
                                        }

                                        3 -> {
                                            viewModel.toggleDayOfWeek(DaysOfWeek.Thursday)
                                            if (counterState.dayOfMonth != 0)
                                                viewModel.handleDatePick(
                                                    counterState.dayOfMonth,
                                                    counterState.month,
                                                    counterState.year
                                                )
                                        }

                                        4 -> {
                                            viewModel.toggleDayOfWeek(DaysOfWeek.Friday)
                                            if (counterState.dayOfMonth != 0)
                                                viewModel.handleDatePick(
                                                    counterState.dayOfMonth,
                                                    counterState.month,
                                                    counterState.year
                                                )
                                        }

                                        5 -> {
                                            viewModel.toggleDayOfWeek(DaysOfWeek.Saturday)
                                            if (counterState.dayOfMonth != 0)
                                                viewModel.handleDatePick(
                                                    counterState.dayOfMonth,
                                                    counterState.month,
                                                    counterState.year
                                                )
                                        }

                                        6 -> {
                                            viewModel.toggleDayOfWeek(DaysOfWeek.Sunday)
                                            if (counterState.dayOfMonth != 0)
                                                viewModel.handleDatePick(
                                                    counterState.dayOfMonth,
                                                    counterState.month,
                                                    counterState.year
                                                )
                                        }
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
            } else {
                when (colorPickerColorToChange) {
                    CounterColor.StartColor -> ColorPicker(
                        startColor = Color(counterState.bgStartColor)
                    ) { color ->
                        viewModel.changeCounterColor(CounterColor.StartColor, color)
                    }

                    CounterColor.CenterColor -> ColorPicker(
                        startColor = Color(counterState.bgCenterColor)
                    ) { color ->
                        viewModel.changeCounterColor(CounterColor.CenterColor, color)
                    }

                    CounterColor.EndColor -> ColorPicker(
                        startColor = Color(counterState.bgEndColor)
                    ) { color ->
                        viewModel.changeCounterColor(CounterColor.EndColor, color)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CounterCreationScreenPreview() {
//    CounterCreationScreen()
}


private fun getCountingTypeResource(countingType: CountingType): Int {
    return when (countingType) {
        CountingType.DAYS -> R.string.rb_days
        CountingType.WEEKS -> R.string.rb_weeks
        CountingType.MONTHS -> R.string.rb_months
        CountingType.YEARS -> R.string.rb_years
    }
}