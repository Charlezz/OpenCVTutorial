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
import org.opencv.imgproc.Imgproc
import org.opencv.imgproc.Imgproc.LINE_AA


@Parcelize
class HoughLinesPTransformImage constructor() : Image(title = "확률적 허프 선 변환", resId = R.drawable.building) {
    override fun process(context: Context, src: Mat): Bitmap? {
        // grayscale로 변환
        val graySrc = Mat()
        Imgproc.cvtColor(src, graySrc, Imgproc.COLOR_BGR2GRAY)

        // 캐니 엣지 검출
        val edge = Mat()
        Imgproc.Canny(graySrc, edge, 50.0, 100.0)

        // 허프 선 변환 함수로 선 검출하기
        val lines = Mat()
        Imgproc.HoughLinesP(
            edge,
            lines,
            1.0, // 1픽셀 간격
            Math.PI / 180.0, // 1도 간격
            100,
            150.0,
            25.0
        )

        // 엣지 영상을 컬러 영상으로 변환
        Imgproc.cvtColor(edge, edge, Imgproc.COLOR_GRAY2BGR)

        // 검출한 선 그리기
        for (x in 0 until lines.rows()) {
            val l: DoubleArray = lines.get(x, 0)
            Imgproc.line(
                edge,
                Point(l[0], l[1]), // 시작점 좌표
                Point(l[2], l[3]), // 끝점 좌표
                Scalar(0.0, 0.0, 255.0),
                3
            )
        }

        return BitmapUtil().bitmapFrom(edge)
    }
}