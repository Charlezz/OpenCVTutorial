package com.charlezz.opencvtutorial.features.common

import android.app.Application
import android.graphics.Bitmap
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

    private var src = MutableLiveData<Mat>()

    var bitmap: LiveData<Bitmap> = Transformations.map(src) { bitmapUtil.bitmapFrom(it) }

    init {
        initialize()
    }

    fun onProcessClick() {
        src.value?.let {
            src.value = processor?.process(it)
        }

    }

    fun onResetClick() {
        initialize()
    }

    private fun initialize(){
        src.value = Utils.loadResource(app, R.drawable.lenna, Imgcodecs.IMREAD_GRAYSCALE)
    }

    override fun onCleared() {
        super.onCleared()
    }

}