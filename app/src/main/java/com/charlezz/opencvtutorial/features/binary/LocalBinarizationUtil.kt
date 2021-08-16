package com.charlezz.opencvtutorial.features.binary

import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

object LocalBinarizationUtil {
    fun process(graySrc:Mat, rows:Int= 4, columns:Int = 4):Mat{

        val copiedSrc = Mat()
        graySrc.copyTo(copiedSrc)

        val width = copiedSrc.width()
        val height = copiedSrc.height()

        for (row in 0 until rows) {
            for (column in 0 until columns) {
                val submat = copiedSrc.submat(
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
        return copiedSrc
    }
}