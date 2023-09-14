package com.example.simpledayscounter.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simpledayscounter.R
import com.example.simpledayscounter.data.enumeration.CountingType

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {

    val homeUiState by viewModel.homeUiState.collectAsState()
    CountersList(
        counterList = homeUiState.counterList,
        modifier = modifier
    )
}

@Composable
fun CountersList(counterList: List<CounterUiState>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.padding(5.dp, 5.dp, 5.dp, 0.dp)
    ) {
        items(items = counterList, key = { counter -> counter.counterId!! }) {

            val timeUnitToDisplay = when (it.countingType) {
                CountingType.DAYS -> R.string.rb_days
                CountingType.WEEKS -> R.string.rb_weeks
                CountingType.MONTHS -> R.string.rb_months
                CountingType.YEARS -> R.string.rb_years
            }

            val direction = if (it.countingDirection == CountingDirection.PAST) {
                R.string.app_widget_counting_text_time_ago
            } else R.string.app_widget_counting_text_time_left

            Counter(
                modifier = Modifier
                    .height(100.dp)
                    .padding(3.dp),
                eventName = it.eventName,
                countingNumber = it.countingNumber,
                countingText = LocalContext.current.getString(
                    direction,
                    (LocalContext.current.getString(timeUnitToDisplay))
                ),
                bgStartColor = it.bgStartColor,
                bgCenterColor = it.bgCenterColor,
                bgEndColor = it.bgEndColor
            )
        }
    }
}

@Composable
fun Counter(
    modifier: Modifier = Modifier,
    eventName: String = stringResource(R.string.app_widget_event_name),
    countingNumber: String = stringResource(R.string.app_widget_counting_number),
    countingText: String = "UiText.DynamicString()",
    bgStartColor: Int = -3052635,
    bgCenterColor: Int = -7952153,
    bgEndColor: Int = -10486799
) {
    val background = Brush.horizontalGradient(
        listOf(
            Color(bgStartColor), Color(bgCenterColor), Color(bgEndColor)
        )
    )

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(background)
            .padding(10.dp)
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = countingNumber,
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = countingText,
                fontSize = 12.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(
            modifier = Modifier
                .width(20.dp)
        )
        Text(
            text = eventName,
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier
                .weight(3f)
                .height(110.dp)
                .fillMaxHeight(),
            textAlign = TextAlign.Left,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
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
        )
    )
}

@Preview(showBackground = true)
@Composable
fun CounterPreview() {
    Counter()
}
