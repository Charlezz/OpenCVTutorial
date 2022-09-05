package com.charlezz.opencvtutorial.presentation.screen.binary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.charlezz.opencvtutorial.*
import com.charlezz.opencvtutorial.presentation.component.ImageCard
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc

@Composable
fun ConvexityDefectsContent() {
    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.hand)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }

    val graySrc by remember {
        mutableStateOf(Mat().also {
            Core.copyTo(src, it, Mat())
            Imgproc.cvtColor(it, it, Imgproc.COLOR_RGB2GRAY)
        })
    }

    val binaryImg = Mat()
    Imgproc.threshold(graySrc, binaryImg, 0.0, 255.0, Imgproc.THRESH_OTSU)

    val dst = Mat().also {
        src.copyTo(it)
    }

    val contours = ArrayList<MatOfPoint>()
    val hierarchy = Mat()

    Imgproc.findContours(
        binaryImg,
        contours,
        hierarchy,
        Imgproc.RETR_TREE,
        Imgproc.CHAIN_APPROX_SIMPLE
    )

    for (i in 0 until contours.size) {
        // 윤곽선 근사화, (근사화 하지 않으면 의도했던 결과보다 많은 볼록결함이 발견된다)
        val contour2f = MatOfPoint2f(*contours[i].toArray())
        val approxContour = MatOfPoint2f()
        Imgproc.approxPolyDP(
            contour2f,
            approxContour,
            Imgproc.arcLength(contour2f, true)*(0.005),
            true
        )
        val convexHull = MatOfInt()
        // 컨벡스 헐 찾기
        val contour = MatOfPoint(*approxContour.toArray())
        Imgproc.convexHull(contour, convexHull)

        // 컨벡스 헐 정점만 추려내기
        val contourArray: Array<Point> = contour.toArray()
        val hullPoints: Array<Point?> = arrayOfNulls<Point>(convexHull.rows())
        val hullContourIdxList = convexHull.toList()
        for (j in hullContourIdxList.indices) {
            hullPoints[j] = contourArray[hullContourIdxList[j]]
        }

        // 처음에 검출한 윤곽선 정보를 토대로 그리기
        Imgproc.drawContours(dst, contours, i, RED, 2)
        // 컨벡스 헐 정보를 토대로 그리기
        Imgproc.drawContours(dst, listOf(MatOfPoint(*hullPoints)),0, GREEN, 2)

        // 볼록결함 찾기
        val convexDefects = MatOfInt4()
        Imgproc.convexityDefects(contour, convexHull, convexDefects)

        val convexDefeatList = convexDefects.toList()
        val vertexArray = contour.toArray()

        for(j in 0 until convexDefeatList.size step 4){
            val start = vertexArray[convexDefeatList[j]]
            val end = vertexArray[convexDefeatList[j+1]]
            // 컨벡스 헐의 시작점(start)과 끝점(end) 사이의 볼록결함
            val def = vertexArray[convexDefeatList[j+2]]
            Imgproc.circle(dst, def, 5, BLUE, 2)
        }

    }

    Column(
        modifier = Modifier.verticalScroll(state = rememberScrollState())
    ) {
        ImageCard(
            title = "Original",
            imageBitmap = src.toBitmap().asImageBitmap()
        )
        ImageCard(
            title = "Convexity Defects",
            imageBitmap = dst.toBitmap().asImageBitmap()
        )
    }
}

