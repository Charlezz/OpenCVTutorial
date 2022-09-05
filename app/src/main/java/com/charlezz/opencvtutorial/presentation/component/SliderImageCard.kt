package com.charlezz.opencvtutorial.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun SliderImageCard(
    modifier: Modifier = Modifier,
    value:Float,
    onValueChange:(Float)->Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    sliderEnabled:Boolean = true,
    title:String = "",
    imageBitmap:ImageBitmap
){
    Column(modifier) {
        Slider(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            enabled = sliderEnabled
        )

        ImageCard(
            title = title,
            imageBitmap = imageBitmap
        )

    }
}