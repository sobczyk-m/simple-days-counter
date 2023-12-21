package com.example.simpledayscounter.presentation.add_counter

import android.nfc.Tag
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.simpledayscounter.R
import com.example.simpledayscounter.data.enumeration.CountingType
import com.example.simpledayscounter.presentation.add_counter.component.common.ColorPicker
import com.example.simpledayscounter.presentation.add_counter.component.common.CustomDatePickerDialog
import com.example.simpledayscounter.presentation.add_counter.component.section.ColorSection
import com.example.simpledayscounter.presentation.add_counter.component.section.CountdownMethodSection
import com.example.simpledayscounter.presentation.add_counter.component.section.DateSelection
import com.example.simpledayscounter.presentation.add_counter.component.section.DayExcludeSection
import com.example.simpledayscounter.presentation.add_counter.component.section.PreviewSection
import com.example.simpledayscounter.presentation.add_counter.component.section.TitleSection
import com.example.simpledayscounter.presentation.add_counter.model.ColorSectionState
import com.example.simpledayscounter.presentation.add_counter.model.DateSectionState
import com.example.simpledayscounter.presentation.add_counter.model.DayExcludeSectionState
import com.example.simpledayscounter.presentation.add_counter.model.PreviewSectionState
import com.example.simpledayscounter.presentation.add_counter.model.enumeration.CounterColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCounterScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AddCounterViewModel = viewModel(factory = AddCounterViewModel.factory)
) {
    val addCounterState = viewModel.addCounterState.collectAsState().value
    val scrollState = rememberScrollState()
    var showDatePicker by remember { mutableStateOf(false) }
    var showColorPicker by remember { mutableStateOf(false) }
    var colorToChange by remember { mutableStateOf(CounterColor.StartColor) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (showColorPicker)
                        Text(stringResource(R.string.choose_color)) else Text(stringResource(R.string.add_counter))
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
                            if (addCounterState.dayOfMonth != 0) {
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
                PreviewSection(
                    state = PreviewSectionState(
                        bgStartColor = addCounterState.bgStartColor,
                        bgCenterColor = addCounterState.bgCenterColor,
                        bgEndColor = addCounterState.bgEndColor,
                        countingDirection = addCounterState.countingDirection,
                        countingNumber = addCounterState.countingNumber,
                        countingType = addCounterState.countingType,
                        eventName = addCounterState.eventName,
                    )
                )
                ColorSection(
                    modifier = Modifier.padding(0.dp, 20.dp, 0.dp, 10.dp),
                    state = ColorSectionState(
                        bgStartColor = addCounterState.bgStartColor,
                        bgCenterColor = addCounterState.bgCenterColor,
                        bgEndColor = addCounterState.bgEndColor
                    ),
                    onBoxClick = { selectedColor ->
                        showColorPicker = true
                        colorToChange = selectedColor
                    })
                Spacer(modifier = Modifier.size(30.dp))
                TitleSection(
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp),
                    title = addCounterState.eventName,
                    changeTitle = { title -> viewModel.changeEventName(title) })
                DateSelection(
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp),
                    state = DateSectionState(
                        dayOfMonth = addCounterState.dayOfMonth,
                        month = addCounterState.month,
                        year = addCounterState.year
                    ),
                    toggleDatePickerDialog = { showDatePicker = !showDatePicker })

                if (showDatePicker) {
                    CustomDatePickerDialog(handleDatePick = { day, month, year ->
                        viewModel.handleDatePick(day, month, year)
                    }, onClose = { showDatePicker = false })
                }

                CountdownMethodSection(
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp),
                    pickedCountingType = addCounterState.countingType,
                    onCountingTypePick = { countingOption ->
                        viewModel.changeCountingType(countingOption)
                        if (addCounterState.dayOfMonth != 0)
                            viewModel.handleDatePick(
                                addCounterState.dayOfMonth,
                                addCounterState.month,
                                addCounterState.year
                            )
                    }
                )

                if (addCounterState.countingType == CountingType.DAYS) {
                    DayExcludeSection(
                        modifier = modifier,
                        state = DayExcludeSectionState(
                            includeMonday = addCounterState.includeMonday,
                            includeTuesday = addCounterState.includeTuesday,
                            includeWednesday = addCounterState.includeWednesday,
                            includeThursday = addCounterState.includeThursday,
                            includeFriday = addCounterState.includeFriday,
                            includeSaturday = addCounterState.includeSaturday,
                            includeSunday = addCounterState.includeSunday
                        ),
                        toggleDayOfWeek = { dayOfWeek ->
                            viewModel.toggleDayOfWeek(dayOfWeek)
                            if (addCounterState.dayOfMonth != 0) {
                                viewModel.handleDatePick(
                                    addCounterState.dayOfMonth,
                                    addCounterState.month,
                                    addCounterState.year
                                )
                            }
                        }
                    )
                }
            } else {
                val startColor = when (colorToChange) {
                    CounterColor.StartColor -> addCounterState.bgStartColor
                    CounterColor.CenterColor -> addCounterState.bgCenterColor
                    CounterColor.EndColor -> addCounterState.bgEndColor
                }

                ColorPicker(
                    startColor = Color(startColor),
                    onColorChange = { color -> viewModel.changeCounterColor(colorToChange, color) },
                    onBackPress = { showColorPicker = false; }
                )
            }
        }
    }
}
