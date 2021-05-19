package com.charlezz.opencvtutorial.features.geometry

import android.content.Context
import android.graphics.Bitmap
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.Image
import com.charlezz.opencvtutorial.R
import kotlinx.parcelize.Parcelize
import org.opencv.core.*
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import kotlin.math.sin


@Parcelize
class RemapWave : Image(R.drawable.runa) {
    override fun process(context: Context, src: Mat): Bitmap? {
        // 연산 속도가 느려서 사이즈를 1/4로 줄임
        Imgproc.resize(src, src, Size(src.size().width/4, src.size().height/4))
        val mapX = Mat(src.size(), CvType.CV_32F)
        val mapY = Mat(src.size(), CvType.CV_32F)


        for(i in 0 until src.rows()){
            for(j in 0 until src.cols()){
                mapX.put(i,j, j.toDouble())
                mapY.put(i,j, i+10* sin(j/10.0))
            }
        }

        Imgproc.remap(src, src, mapX, mapY, Imgproc.INTER_CUBIC, Core.BORDER_DEFAULT)
        return BitmapUtil().bitmapFrom(src)
    }

}