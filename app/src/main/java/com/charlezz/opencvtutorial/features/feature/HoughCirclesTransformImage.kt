package com.charlezz.opencvtutorial.features.feature

import android.content.Context
import android.graphics.Bitmap
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.Image
import com.charlezz.opencvtutorial.R
import kotlinx.parcelize.Parcelize
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc


@Parcelize
class HoughCirclesTransformImage : Image(title = "허프 원 변환", resId = R.drawable.coin) {
    override fun process(context: Context, src: Mat): Bitmap? {

        // grayscale로 변환
        val graySrc = Mat()
        Imgproc.cvtColor(src, graySrc, Imgproc.COLOR_BGR2GRAY)

        // 좀 더 정확한 검출을 위해 잡음 제거를 위한 가우시안 블러처리
        val blurred = Mat()
        Imgproc.GaussianBlur(graySrc, blurred, Size(0.0, 0.0), 1.0)

        // 허프 원 변환을 통한 원 검출
        val circles = Mat()
        Imgproc.HoughCircles(
            blurred,
            circles,
            Imgproc.HOUGH_GRADIENT,
            1.0, // 입력 영상과 누적배열 비율
            50.0, // 검출된 원 중심점들의 최소 거리
            150.0, // Canny 에지 검출기의 높은 임계값
            40.0, // 누적 배열에서 원 검출을 위한 임계값
            50, // 원의 최소 반지름
            150 // 원의 최대 반지름
        )

        // 검출한 원에 덧그리기
        for (i in 0 until circles.cols()) {
            val circle = circles.get(0, i) // 검출된 원
            val centerX = circle[0] // 원의 중심점 X좌표
            val centerY = circle[1] //원의 중심점 Y좌표
            val radius = Math.round(circle[2]).toInt() // 원의 반지름

            val center = Point(
                Math.round(centerX).toDouble(),
                Math.round(centerY).toDouble()
            )

            val centerColor = Scalar(0.0, 0.0, 255.0)
            Imgproc.circle(src, center, 3, centerColor, 3)

            val circleColor = Scalar(255.0, 0.0, 255.0)
            Imgproc.circle(src, center, radius, circleColor, 3)
        }

        return BitmapUtil().bitmapFrom(src)
    }
}