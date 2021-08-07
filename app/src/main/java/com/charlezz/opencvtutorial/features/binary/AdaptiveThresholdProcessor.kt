package com.charlezz.opencvtutorial.features.binary

import com.charlezz.opencvtutorial.features.common.SliderProcessor
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class AdaptiveThresholdProcessor : SliderProcessor(3F, 500F) {
    override fun process(src: Mat, value1: Float, value2: Float, value3: Float): Mat {
        val dst = Mat()
        var blockSize = value1.toInt()
        if (blockSize % 2 == 0) {
            blockSize -= 1
        }
        Imgproc.adaptiveThreshold(
            src, dst, 255.0,
            Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
            Imgproc.THRESH_BINARY,
            blockSize,
            5.0
        )
        return dst
    }
}