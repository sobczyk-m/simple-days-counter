package com.example.simpledayscounter.presentation.add_counter.component.section

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpledayscounter.R
import com.example.simpledayscounter.presentation.add_counter.model.DateSectionState

@Composable
fun DateSelection(
    modifier: Modifier = Modifier,
    state: DateSectionState,
    toggleDatePickerDialog: () -> Unit
) {
    Column(
        modifier = modifier
    )
    {
        Text(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 10.dp),
            text = stringResource(id = R.string.tv_countdown_date)
        )
        TextField(
            modifier = Modifier
                .clickable { toggleDatePickerDialog() },
            placeholder = { Text(text = stringResource(id = R.string.et_select_event_date)) },
            enabled = false,
            value = if (state.dayOfMonth == 0) "" else "${state.dayOfMonth}/${state.month}/${state.year}",
            onValueChange = {},
            colors = TextFieldDefaults.colors(
                disabledPlaceholderColor = Color.Black,
                disabledTextColor = Color.Black
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DateSelectionPreview() {
    val dateSectionState = DateSectionState(
        dayOfMonth = 25,
        month = 12,
        year = 2023
    )

    DateSelection(
        modifier = Modifier,
        state = dateSectionState,
        toggleDatePickerDialog = { }
    )
}