package com.charlezz.opencvtutorial.presentation.screen.filter

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc

/**
 * @author soohwan.ok
 */
@Composable
fun BilateralFilterContent(
    modifier: Modifier = Modifier
) {
    var count by remember { mutableStateOf(0) }
    val context = LocalContext.current
    var src by remember {
        mutableStateOf(
            Utils.loadResource(
                context,
                R.drawable.lenna,
                Imgcodecs.IMREAD_COLOR
            )
        )
    }
    Column {
        OutlinedButton(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            onClick = {
                count++
                val dst = Mat()
                Imgproc.bilateralFilter(
                    src,
                    dst,
                    -1, // 필터링에 사용될 이웃 픽셀의 거리(지름). -1을 입력하면 sigmaSpace에 의해 자동 결정
                    10.0, // 색 공간에서 필터의 표준 편차
                    5.0 // 좌표 공간에서 필터의 표준 편차
                )
                src = dst
            }
        ) {
            Text(
                text = "Accumulate Filter ${if (count == 0) "" else "+$count"}"
            )
        }

        val dst = Mat()
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2RGB)

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            bitmap = dst.toBitmap().asImageBitmap(),
            contentScale = ContentScale.Fit,
            contentDescription = null
        )
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
private fun BilateralFilterContentPreview() {
    BilateralFilterContent()
}