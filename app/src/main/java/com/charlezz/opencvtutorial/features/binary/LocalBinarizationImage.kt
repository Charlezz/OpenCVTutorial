package com.charlezz.opencvtutorial.features.binary

import android.content.Context
import android.graphics.Bitmap
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.Image
import com.charlezz.opencvtutorial.R
import kotlinx.parcelize.Parcelize
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

@Parcelize
class LocalBinarizationImage(
    private val _title: String,
    private val drawableResId: Int,
    private val columns: Int,
    private val rows: Int
) : Image(_title, drawableResId) {
    override fun process(context: Context, src: Mat): Bitmap? {
        // grayscale로 변환
        val graySrc = Mat()
        Imgproc.cvtColor(src, graySrc, Imgproc.COLOR_BGR2GRAY)

        val width = graySrc.width()
        val height = graySrc.height()

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
        return BitmapUtil().bitmapFrom(graySrc)
    }
}