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
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.round
import kotlin.math.sin

@Parcelize
class HoughLinesTransformImage constructor() : Image(title = "허프 선 변환", resId = R.drawable.building) {
    override fun process(context: Context, src: Mat): Bitmap? {
        // grayscale로 변환
        val graySrc = Mat()
        Imgproc.cvtColor(src, graySrc, Imgproc.COLOR_BGR2GRAY)

        // 캐니 엣지 검출
        val edge = Mat()
        Imgproc.Canny(graySrc, edge, 50.0, 100.0)

        // 허프 선 변환 함수로 선 검출하기
        val lines = Mat()
        Imgproc.HoughLines(
            edge,
            lines,
            1.0, // 1픽셀 간격
            Math.PI / 180.0, // 1도 간격
            300
        )

        // 엣지 영상을 컬러 영상으로 변환
        Imgproc.cvtColor(edge, edge, Imgproc.COLOR_GRAY2BGR)

        // 검출한 선 그리기
        for (x in 0 until lines.rows()) {
            val rho = lines.get(x, 0)[0]
            val theta = lines.get(x, 0)[1]

            val a = cos(theta)
            val b = sin(theta)

            val x0 = a * rho
            val y0 = b * rho

            val pt1 = Point(round(x0 + 5000*(-b)), round(y0 + 5000*(a)))
            val pt2 = Point(round(x0 - 5000*(-b)), round(y0 - 5000*(a)))

            Imgproc.line(edge, pt1, pt2, Scalar(0.0,0.0,255.0), 3)
        }

        return BitmapUtil().bitmapFrom(edge)
    }
}