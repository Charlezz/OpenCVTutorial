package com.charlezz.opencvtutorial.features.binary

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.Image
import com.charlezz.opencvtutorial.R
import kotlinx.parcelize.Parcelize
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Rect
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc

@Parcelize
class LabelingImage constructor(
    private val _title: String,
    @DrawableRes private val drawableResId: Int = R.drawable.rice
) : Image(_title, drawableResId) {
    override fun process(context: Context, src: Mat): Bitmap? {
        val rows = 4
        val columns = 4

        // grayscale로 변환
        val graySrc = Mat()
        Imgproc.cvtColor(src, graySrc, Imgproc.COLOR_BGR2GRAY)

        val width = graySrc.width()
        val height = graySrc.height()

        //지역 이진화
        val binarized = LocalBinarizationUtil.process(graySrc)

        // 객체에 대한 정보 저장 할 행렬들
        val labels = Mat()
        val stats = Mat()
        val centroids = Mat()
        val objectCount = Imgproc.connectedComponentsWithStats(
            binarized,
            labels,
            stats,
            centroids
        )

        // 출력될 이미지
        val dst = Mat()

        // 색상 변환
        Imgproc.cvtColor(binarized, dst, Imgproc.COLOR_GRAY2BGR)

        // 객체 바운딩 박스 그리기
        var riceCount = 0
        for (index in 1 until stats.rows()) {
            val x = stats.row(index).get(0, 0)[0].toInt()
            val y = stats.row(index).get(0, 1)[0].toInt()
            val width = stats.row(index).get(0, 2)[0].toInt()
            val height = stats.row(index).get(0, 3)[0].toInt()
            val area = stats.row(index).get(0, 4)[0].toInt()

            if(area > 10L){
                riceCount++
                Imgproc.rectangle(
                    dst,
                    Rect(x, y, width, height),
                    Scalar(0.0, 0.0, 255.0),
                    3
                )

                // 무게중심 점찍기
                val centerX = centroids.row(index).get(0,0)[0].toInt()
                val centerY = centroids.row(index).get(0,1)[0].toInt()
                Imgproc.circle(dst, Point(centerX.toDouble(), centerY.toDouble()), 5, Scalar(255.0,0.0,0.0),5)
            }
        }

//
//        for (index in 1 until centroids.rows()) {
//
//        }

        // 쌀알 갯수 출력
//        Imgproc.putText(dst, "Count = $count", Point((dst.cols()/3).toDouble(),(dst.rows()/2).toDouble()), Imgproc.FONT_HERSHEY_COMPLEX, 2.0, Scalar(0.0,255.0,0.0), 3)
        result = "레이블링 갯수 = $objectCount, 쌀알 갯수 = $riceCount"

        return BitmapUtil().bitmapFrom(dst)
    }
}