package com.example.simpledayscounter.presentation.counters.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simpledayscounter.R

@Composable
fun Counter(
    modifier: Modifier = Modifier,
    eventName: String = stringResource(R.string.app_widget_event_name),
    countingNumber: String = "0",
    countingText: String = stringResource(
        R.string.app_widget_counting_text_time_left, stringResource(R.string.rb_days)
    ),
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
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = countingText,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
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
fun CounterPreview() {
    Counter()
}