package com.charlezz.opencvtutorial.features.binary

import com.charlezz.opencvtutorial.features.common.SliderProcessor
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class MorphologyProcessor(val erosion:Boolean) : SliderProcessor(0F, 10F) {
    override fun process(
        src: Mat,
        value1: Float,
        value2: Float,
        value3: Float
    ): Mat {

        val graySrc = Mat()
        src.copyTo(graySrc)

        val binarized = LocalBinarizationUtil.process(graySrc)

        val kernel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, Size(3.0, 3.0))
        for(i in 0 until value1.toInt()){
            if(erosion){
                Imgproc.erode(binarized, binarized, kernel)
            }else{
                Imgproc.dilate(binarized, binarized, kernel)
            }
        }

        return binarized
    }
}