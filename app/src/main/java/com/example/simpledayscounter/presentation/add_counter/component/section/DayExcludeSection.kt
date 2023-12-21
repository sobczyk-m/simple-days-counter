package com.example.simpledayscounter.presentation.add_counter.component.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpledayscounter.R
import com.example.simpledayscounter.presentation.add_counter.model.DayExcludeSectionState
import com.example.simpledayscounter.presentation.add_counter.model.enumeration.DaysOfWeek

@Composable
fun DayExcludeSection(
    modifier: Modifier = Modifier,
    state: DayExcludeSectionState,
    toggleDayOfWeek: (dayOfWeek: DaysOfWeek) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp),
            text = stringResource(id = R.string.tv_day_exclude)
        )
        Column(modifier = Modifier) {
            DaysOfWeek.entries.forEachIndexed { index, dayOfWeek ->
                Row(
                    modifier = modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val isChecked = when (index) {
                        0 -> state.includeMonday
                        1 -> state.includeTuesday
                        2 -> state.includeWednesday
                        3 -> state.includeThursday
                        4 -> state.includeFriday
                        5 -> state.includeSaturday
                        6 -> state.includeSunday
                        else -> false
                    }

                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { toggleDayOfWeek(DaysOfWeek.entries[index]) }
                    )
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
fun DayExcludeSectionPreview() {
    val dayExcludeSectionState = DayExcludeSectionState(
        includeMonday = true,
        includeTuesday = false,
        includeWednesday = true,
        includeThursday = false,
        includeFriday = true,
        includeSaturday = false,
        includeSunday = true
    )

    DayExcludeSection(
        modifier = Modifier,
        state = dayExcludeSectionState,
        toggleDayOfWeek = { }
    )
}