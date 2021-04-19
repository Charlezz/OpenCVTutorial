package com.charlezz.opencvtutorial.features.filtering

import com.charlezz.opencvtutorial.features.common.Processor
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import javax.inject.Inject

/**
 * 평균 값 필터(Mean Filter)
 * 영상의 특정 좌표 값을 주변 픽셀 값들의 산술 평균으로 설정
 */
class Filter2DProcessor @Inject constructor() : Processor {

    private val kernelSize = 5

    override fun process(src: Mat): Mat {
        val dst = Mat()
        val kernel = Mat(kernelSize, kernelSize, CvType.CV_32F)
        for(i in 0 until kernelSize){
            for(j in 0 until kernelSize){
                kernel.put(i, j, 1.0 / (kernelSize*kernelSize).toDouble())
            }
        }
        Imgproc.filter2D(src, dst, -1, kernel)
        return dst
    }
}