package com.charlezz.opencvtutorial.features.binary

import com.charlezz.opencvtutorial.RED
import com.charlezz.opencvtutorial.features.common.SliderProcessor
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.MatOfPoint2f
import org.opencv.imgproc.Imgproc

class ApproxPolyDPProcessor() : SliderProcessor(0F, 10F) {
    override fun process(
        src: Mat,
        value1: Float,
        value2: Float,
        value3: Float
    ): Mat {
        val copiedSrc = Mat()
        src.copyTo(copiedSrc)

        val graySrc = Mat()
        Imgproc.cvtColor(copiedSrc, graySrc, Imgproc.COLOR_BGR2GRAY)

        val binarySrc = Mat()
        Imgproc.threshold(graySrc, binarySrc, 0.0, 255.0, Imgproc.THRESH_OTSU)

        val contours = ArrayList<MatOfPoint>()
        val hierarchy = Mat()
        Imgproc.findContours(binarySrc, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE)


        for(contour in contours){
            val contour2f = MatOfPoint2f(*contour.toArray())
            val approxCurve = MatOfPoint2f()
            Imgproc.approxPolyDP(
                contour2f,
                approxCurve,
                Imgproc.arcLength(contour2f, true)*(0.005*value1),
                true
            )

            val points = approxCurve.toList()
            for(i in points.indices){
                Imgproc.line(copiedSrc, points[i], points[(i+1)%points.size], RED, 2)
            }
        }
        return copiedSrc
    }
}