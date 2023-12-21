package com.example.simpledayscounter.presentation.add_counter.component.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpledayscounter.R
import com.example.simpledayscounter.data.enumeration.CountingType
import com.example.simpledayscounter.utils.getCountingTypeResource

@Composable
fun CountdownMethodSection(
    modifier: Modifier = Modifier,
    pickedCountingType: CountingType,
    onCountingTypePick: (countingType: CountingType) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 10.dp),
            text = stringResource(id = R.string.tv_counting_method)
        )
        Row(
            modifier = Modifier, horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CountingType.entries.forEach { type ->
                RadioButton(
                    selected = pickedCountingType == type,
                    onClick = {
                        onCountingTypePick(type)
                    },
                )
                Text(
                    text = stringResource(id = getCountingTypeResource(type))
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CountdownMethodSectionPreview() {

    CountdownMethodSection(
        modifier = Modifier,
        pickedCountingType = CountingType.WEEKS,
        onCountingTypePick = { }
    )
}