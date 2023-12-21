package com.example.simpledayscounter.presentation.add_counter.component.common

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    handleDatePick: (day: Int, month: Int, year: Int) -> Unit,
    onClose: () -> Unit
) {
    val snackState = remember { SnackbarHostState() }
    val dpdScope = rememberCoroutineScope()
    SnackbarHost(hostState = snackState, Modifier)
    val openDialog = remember { mutableStateOf(true) }

    DisposableEffect(Unit) {
        onDispose {
            onClose()
        }
    }

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

                            handleDatePick(selectedDay, selectedMonth, selectedYear)
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

@Preview(showBackground = true)
@Composable
fun CustomDatePickerDialogPreview() {
    CustomDatePickerDialog({ _, _, _ -> { } }, onClose = {})
}