package com.example.simpledayscounter.presentation.add_counter.component.section

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpledayscounter.R
import com.example.simpledayscounter.presentation.add_counter.model.ColorSectionState
import com.example.simpledayscounter.presentation.add_counter.model.enumeration.CounterColor

@Composable
fun ColorSection(
    modifier: Modifier = Modifier,
    state: ColorSectionState,
    onBoxClick: (selectedColor: CounterColor) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        val times = 3
        repeat(times) { i ->
            Row(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, if (i < times - 1) 10.dp else 0.dp)
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
                            val colorToChange = when (i) {
                                0 -> CounterColor.StartColor
                                1 -> CounterColor.CenterColor
                                2 -> CounterColor.EndColor
                                else -> CounterColor.StartColor
                            }
                            onBoxClick(colorToChange)
                        },
                        colors = ButtonDefaults.buttonColors(
                            Color(
                                when (i) {
                                    0 -> state.bgStartColor
                                    1 -> state.bgCenterColor
                                    2 -> state.bgEndColor
                                    else -> state.bgStartColor
                                }
                            )
                        )
                    ) {}
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColorSectionPreview() {
    val colorSectionState = ColorSectionState(
        bgStartColor = Color.Red.toArgb(),
        bgCenterColor = Color.Green.toArgb(),
        bgEndColor = Color.Blue.toArgb()
    )

    ColorSection(
        modifier = Modifier,
        state = colorSectionState,
        onBoxClick = { }
    )
}