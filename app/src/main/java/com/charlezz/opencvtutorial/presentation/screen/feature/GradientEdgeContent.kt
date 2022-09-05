package com.charlezz.opencvtutorial.presentation.screen.feature

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.presentation.component.ImageCard
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

@Composable
fun GradientEdgeContent() {
    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.runa)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }
    val graySrc by remember {
        mutableStateOf( Mat().also {
            Core.copyTo(src, it, Mat())
            Imgproc.cvtColor(it, it, Imgproc.COLOR_RGB2GRAY)
        })
    }

    //x방향으로 소벨필터 적용
    val dx = Mat()
    Imgproc.Sobel(graySrc, dx, CvType.CV_32F, 1, 0, 3)

    //y방향으로 소벨필터 적용
    val dy = Mat()
    Imgproc.Sobel(graySrc, dy, CvType.CV_32F, 0, 1, 3)

    //위의 두개의 행렬을 이용하여 그라디언트 크기 구하기
    val mag = Mat()
    Core.magnitude(dx, dy, mag)

    // Float형으로 계산된 결과기 때문에 비트맵으로 표현 할 수 있는 타입으로 변경
    mag.convertTo(mag, CvType.CV_8UC1)

    // 뚜렷한 엣지만 검출하기 위해 밝은 픽셀만 검출
    Imgproc.threshold(mag, mag, 70.0, 255.0, Imgproc.THRESH_BINARY)

    LazyColumn {
        item {
            ImageCard(
                title = "Original",
                imageBitmap = src.toBitmap().asImageBitmap()
            )
        }
        item {
            ImageCard(
                title = "Gradient edge detection",
                imageBitmap = mag.toBitmap().asImageBitmap()
            )
        }
    }
}
