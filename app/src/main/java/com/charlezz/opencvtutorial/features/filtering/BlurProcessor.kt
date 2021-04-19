package com.charlezz.opencvtutorial.features.filtering

import com.charlezz.opencvtutorial.features.common.Processor
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import javax.inject.Inject

/**
 * 평균 값 필터
 * @see Filter2DProcessor 와 동일한 효과
 */
class BlurProcessor @Inject constructor() : Processor {
    private val kernelSize = 5.0
    override fun process(src: Mat): Mat {
        val dst = Mat()
        Imgproc.blur(src, dst, Size(kernelSize, kernelSize))
        return dst
    }
}