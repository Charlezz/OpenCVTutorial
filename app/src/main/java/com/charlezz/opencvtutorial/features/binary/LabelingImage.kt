package com.charlezz.opencvtutorial.features.binary

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
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
import timber.log.Timber

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
        for (row in 0 until rows) {
            for (column in 0 until columns) {
                val submat = graySrc.submat(
                    height / rows * row,
                    height / rows * (row + 1),
                    width / columns * column,
                    width / columns * (column + 1)
                )
                Imgproc.threshold(
                    submat,
                    submat,
                    0.0,
                    255.0,
                    Imgproc.THRESH_BINARY or Imgproc.THRESH_OTSU
                )
            }
        }

        // 객체에 대한 정보 저장 할 행렬들
        val labels = Mat()
        val stats = Mat()
        val centroids = Mat()
        val count = Imgproc.connectedComponentsWithStats(
            graySrc,
            labels,
            stats,
            centroids
        )

        // 출력될 이미지
        val dst = Mat()

        // 색상 변환
        Imgproc.cvtColor(graySrc, dst, Imgproc.COLOR_GRAY2BGR)

        // 객체 바운딩 박스 그리기
        for (index in 1 until stats.rows()) {
            val x = stats.row(index).get(0, 0)[0].toInt()
            val y = stats.row(index).get(0, 1)[0].toInt()
            val width = stats.row(index).get(0, 2)[0].toInt()
            val height = stats.row(index).get(0, 3)[0].toInt()

            Imgproc.rectangle(
                dst,
                Rect(x, y, width, height),
                Scalar(0.0, 0.0, 255.0),
                3
            )
        }

        // 무게중심 점찍기
        for (index in 1 until centroids.rows()) {
            val centerX = centroids.row(index).get(0,0)[0].toInt()
            val centerY = centroids.row(index).get(0,1)[0].toInt()
            Imgproc.circle(dst, Point(centerX.toDouble(), centerY.toDouble()), 5, Scalar(255.0,0.0,0.0),5)
        }

        // 쌀알 갯수 출력
        Imgproc.putText(dst, "Count = $count", Point((dst.cols()/3).toDouble(),(dst.rows()/2).toDouble()), Imgproc.FONT_HERSHEY_COMPLEX, 2.0, Scalar(0.0,255.0,0.0), 3)

        return BitmapUtil().bitmapFrom(dst)
    }
}