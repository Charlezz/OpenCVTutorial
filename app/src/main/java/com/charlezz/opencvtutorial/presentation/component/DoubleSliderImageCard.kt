package com.charlezz.opencvtutorial.presentation.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.charlezz.opencvtutorial.R

@Composable
fun DoubleSliderImageCard(
    modifier: Modifier = Modifier,
    value1Range: ClosedFloatingPointRange<Float>,
    value1:Float,
    onValue1Change:(Float)->Unit,
    value2Range: ClosedFloatingPointRange<Float>,
    value2:Float,
    onValue2Change:(Float)->Unit,
    title:String = "",
    imageBitmap:ImageBitmap
){
    Column(modifier) {
        Slider(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            value = value1,
            onValueChange = onValue1Change,
            valueRange = value1Range,
        )
        Slider(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            value = value2,
            onValueChange = onValue2Change,
            valueRange = value2Range,
        )

        ImageCard(
            title = title,
            imageBitmap = imageBitmap
        )

    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DoubleSliderImageCardPreview() {
    var value1 by remember { mutableStateOf(50f) }
    var value2 by remember { mutableStateOf(50f) }
    DoubleSliderImageCard(
        value1 = value1,
        value1Range = 0f..100f,
        onValue1Change = {value1 = it},
        value2 = value2,
        value2Range = 0f..100f,
        onValue2Change = {value2 = it},
        imageBitmap = ImageBitmap.imageResource(id = R.drawable.runa)
    )
}