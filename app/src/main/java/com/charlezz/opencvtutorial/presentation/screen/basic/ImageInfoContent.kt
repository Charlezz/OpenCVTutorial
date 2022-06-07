package com.charlezz.opencvtutorial.presentation.screen.basic

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.charlezz.opencvtutorial.R
import org.opencv.android.Utils
import org.opencv.core.Mat


/**
 * @author soohwan.ok
 */
@Composable
fun ImageInfoContent() {
    val resourceId = R.drawable.lenna
    val context: Context = LocalContext.current
    val bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, resourceId)
    val mat = Mat()
    Utils.bitmapToMat(bitmap, mat)

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                state = scrollState
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            painter = painterResource(id = resourceId),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = """
width = ${mat.width()}
height = ${mat.height()}
pixels = ${mat.total()}
type = ${mat.type()}
channels = ${mat.channels()}
            """
        )
    }
}

@Preview
@Composable
fun ImageInfoContentPreview() {
    ImageInfoContent()
}