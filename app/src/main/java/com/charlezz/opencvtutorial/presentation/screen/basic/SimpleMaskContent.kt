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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc


/**
 * @author soohwan.ok
 */
@Composable
fun SimpleMaskContent() {
    val context: Context = LocalContext.current
    val imgResId = R.drawable.lenna
    val maskResId = R.drawable.mask_circle
    val imageMat = Utils.loadResource(context, imgResId)
    val maskMat = Utils.loadResource(context, maskResId, CvType.CV_8U)

    val dst = Mat(imageMat.rows(), imageMat.cols(), CvType.CV_8UC3)
    Core.copyTo(imageMat, dst, maskMat)

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
        Text("Original Image")
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            painter = painterResource(id = imgResId),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        Text("Mask Image")
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            painter = painterResource(id = maskResId),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        Text("Masked Image")
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            bitmap = dst.toBitmap(Imgproc.COLOR_BGR2RGBA).asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

    }
}
