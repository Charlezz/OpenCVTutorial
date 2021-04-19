package com.charlezz.opencvtutorial.features.filtering

import com.charlezz.opencvtutorial.features.common.Processor
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import javax.inject.Inject

/**
 * 주변 픽셀들의 값들을 정렬하여 그 중앙값(median)으로 픽셀값을 대체
 */
class MedianFilterProcessor @Inject constructor() : Processor {
    private val kernelSize = 5
    override fun process(src: Mat): Mat {
        val dst = Mat()
        Imgproc.medianBlur(src, dst, kernelSize)
        return dst
    }
}