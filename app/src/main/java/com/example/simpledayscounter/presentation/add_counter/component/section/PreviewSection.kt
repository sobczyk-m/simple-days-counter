package com.example.simpledayscounter.presentation.add_counter.component.section

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.simpledayscounter.R
import com.example.simpledayscounter.data.enumeration.CountingType
import com.example.simpledayscounter.presentation.add_counter.model.PreviewSectionState
import com.example.simpledayscounter.presentation.counters.components.Counter
import com.example.simpledayscounter.presentation.counters.constants.CountingDirection
import com.example.simpledayscounter.utils.getCountingTypeResource

@Composable
fun PreviewSection(
    modifier: Modifier = Modifier,
    state: PreviewSectionState
) {
    Row(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Counter(
            modifier = Modifier.weight(8f),
            eventName = if (state.eventName.isEmpty())
                stringResource(id = R.string.app_widget_event_name) else state.eventName,
            countingNumber = when (state.countingDirection) {
                CountingDirection.PAST -> "-${state.countingNumber}"
                CountingDirection.FUTURE -> state.countingNumber
            },
            countingText = when (state.countingDirection) {
                CountingDirection.PAST -> stringResource(
                    id = R.string.app_widget_counting_text_time_ago,
                    stringResource(id = getCountingTypeResource(state.countingType))
                )

                CountingDirection.FUTURE -> stringResource(
                    id = R.string.app_widget_counting_text_time_left,
                    stringResource(id = getCountingTypeResource(state.countingType))
                )
            },
            bgStartColor = state.bgStartColor,
            bgCenterColor = state.bgCenterColor,
            bgEndColor = state.bgEndColor,
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSectionPreview() {
    val previewSectionState = PreviewSectionState(
        eventName = "Sample Event",
        countingDirection = CountingDirection.FUTURE,
        countingNumber = "10",
        countingType = CountingType.DAYS,
        bgStartColor = Color.Red.toArgb(),
        bgCenterColor = Color.Green.toArgb(),
        bgEndColor = Color.Blue.toArgb()
    )

    PreviewSection(
        modifier = Modifier,
        state = previewSectionState
    )
}