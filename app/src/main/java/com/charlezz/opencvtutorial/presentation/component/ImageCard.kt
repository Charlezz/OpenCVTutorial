package com.charlezz.opencvtutorial.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.charlezz.opencvtutorial.R

/**
 * @author soohwan.ok
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCard(
    modifier: Modifier = Modifier
        .padding(vertical = 10.dp, horizontal = 16.dp),
    title: String,
    imageBitmap: ImageBitmap
) {
    OutlinedCard(modifier = modifier) {
        Column(modifier = Modifier.padding(20.dp)) {
            if(title.isNotBlank()){
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title
                )
                Spacer(modifier = Modifier.size(5.dp))
            }
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(imageBitmap.width.toFloat() / imageBitmap.height.toFloat()),
                bitmap = imageBitmap,
                contentDescription = null,
            )
        }

    }
}

@Preview
@Composable
private fun ImageCardPreview() {
    ImageCard(
        title = "Title",
        imageBitmap = ImageBitmap.imageResource(id = R.drawable.runa)
    )
}