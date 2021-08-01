package com.charlezz.opencvtutorial.features.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcelable
import androidx.lifecycle.LiveData
import com.charlezz.opencvtutorial.R
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import java.io.Serializable

abstract class SliderProcessor constructor(
    val valueFrom1: Float,
    val valueTo1: Float,
    val valueFrom2: Float,
    val valueTo2: Float,
    val valueFrom3: Float,
    val valueTo3: Float,
) : Serializable {

    var slider1Visible: Boolean = true
    var slider2Visible: Boolean = true
    var slider3Visible: Boolean = true

    constructor(
        valueFrom1: Float,
        valueTo1: Float,
        valueFrom2: Float,
        valueTo2: Float
    ) : this(
        valueFrom1,
        valueTo1,
        valueFrom2,
        valueTo2,
        -1F,
        -1F
    ) {
        slider3Visible = false
    }

    constructor(
        valueFrom1: Float,
        valueTo1: Float
    ) : this(valueFrom1, valueTo1, -1F, -1F, -1F, -1F){
        slider2Visible = false
        slider3Visible = false
    }

    abstract fun process(src:Mat, value1: Float, value2: Float, value3: Float):Mat
}