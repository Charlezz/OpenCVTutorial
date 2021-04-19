package com.charlezz.opencvtutorial.features.filtering

import com.charlezz.opencvtutorial.features.common.Processor
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import javax.inject.Inject


class Filter2DProcessor @Inject constructor() : Processor {
    override fun process(src: Mat): Mat {
        val dst = Mat(src.rows(), src.cols(), src.type())
        val kernel = Mat(3, 3, CvType.CV_32F)

        kernel.put(0, 0, 1.0 / 9.0)
        kernel.put(0, 1, 1.0 / 9.0)
        kernel.put(0, 2, 1.0 / 9.0)

        kernel.put(1, 0, 1.0 / 9.0)
        kernel.put(1, 1, 1.0 / 9.0)
        kernel.put(1, 2, 1.0 / 9.0)

        kernel.put(2, 0, 1.0 / 9.0)
        kernel.put(2, 1, 1.0 / 9.0)
        kernel.put(2, 2, 1.0 / 9.0)

        Imgproc.filter2D(src, dst, -1, kernel)
        return dst
    }
}