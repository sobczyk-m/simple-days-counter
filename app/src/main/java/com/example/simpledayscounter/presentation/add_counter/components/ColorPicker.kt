package com.example.simpledayscounter.presentation.add_counter.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun ColorPicker(
    startColor: Color,
    onColorChange: (Int) -> Unit,
) {
    var currentColor by remember { mutableStateOf(startColor) }
    val controller = rememberColorPickerController()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 30.dp)
    ) {
        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .padding(10.dp),
            initialColor = currentColor,
            controller = controller,
            onColorChanged = {
                onColorChange(controller.selectedColor.value.toArgb())
            }
        )
        AlphaSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(35.dp)
                .border(BorderStroke(3.dp, Color.Black)),
            controller = controller,
            initialColor = currentColor,
            tileOddColor = Color.White,
            tileEvenColor = Color.Black
        )
        BrightnessSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(35.dp)
                .border(BorderStroke(3.dp, Color.Black)),
            initialColor = currentColor,
            controller = controller,
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AlphaTile(
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .border(BorderStroke(3.dp, Color.Black)),
                controller = controller
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColorPickerPreview() {
    ColorPicker(startColor = Color(-3052635), { _ -> {} })
}