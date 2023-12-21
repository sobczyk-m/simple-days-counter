package com.example.simpledayscounter.presentation.add_counter.component.section

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

@Composable
fun TitleSection(
    modifier: Modifier = Modifier,
    title: String,
    changeTitle: (title: String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 10.dp),
            text = stringResource(id = R.string.tv_countdown_title)
        )
        TextField(
            modifier = Modifier,
            placeholder = { Text(text = stringResource(id = R.string.et_countdown_title)) },
            value = title,
            colors = TextFieldDefaults.colors(disabledPlaceholderColor = Color.Black),
            onValueChange = { changeTitle(it) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TitleSectionPreview() {
    TitleSection(
        modifier = Modifier,
        title = "title",
        changeTitle = { }
    )
}