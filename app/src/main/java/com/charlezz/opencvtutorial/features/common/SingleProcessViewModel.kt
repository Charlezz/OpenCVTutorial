package com.charlezz.opencvtutorial.features.common

import android.app.Application
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.lifecycle.*
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.features.filtering.Filter2DProcessor
import dagger.hilt.android.lifecycle.HiltViewModel
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import javax.inject.Inject

@HiltViewModel
class SingleProcessViewModel @Inject constructor(val app: Application) : AndroidViewModel(app) {

    @Inject
    lateinit var bitmapUtil: BitmapUtil

    var processor: Processor? = null

    private var resId:Int = R.drawable.lenna
    private var imreadMode:Int = Imgcodecs.IMREAD_GRAYSCALE
    private val src = MutableLiveData<Mat>()

    var bitmap: LiveData<Bitmap> = Transformations.map(src) { bitmapUtil.bitmapFrom(it) }

    fun onProcessClick() {
        src.value?.let {
            src.value = processor?.process(it)
        }

    }

    fun onResetClick() {
        loadImage(resId, imreadMode)
    }

    fun loadImage(@DrawableRes resId:Int, imreadMode:Int){
        this.resId = resId
        this.imreadMode = imreadMode
        this.src.value = Utils.loadResource(app, resId, imreadMode)
    }

    override fun onCleared() {
        super.onCleared()
    }

}