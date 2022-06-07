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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat


/**
 * @author soohwan.ok
 */
@Composable
fun ImageChannelsContent() {
    val resourceId = R.drawable.lenna
    val context: Context = LocalContext.current
    val bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, resourceId)
    val mat = Mat()
    Utils.bitmapToMat(bitmap, mat)

    val channels = ArrayList<Mat>()
    Core.split(mat, channels)

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
        channels.forEach { mat ->
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                bitmap = mat.toBitmap().asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        }

    }
}