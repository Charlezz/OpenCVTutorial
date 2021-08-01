package com.charlezz.opencvtutorial.features.binary

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.features.common.SliderProcessor
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class ThresholdProcessor : SliderProcessor(0F, 255F) {

    override fun process(
        src : Mat,
        value1: Float,
        value2: Float,
        value3: Float
    ):Mat {
        val dst = Mat()
        Imgproc.threshold(src, dst, value1.toDouble(), 255.0, Imgproc.THRESH_BINARY)
        return dst
    }

}