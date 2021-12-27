package com.charlezz.opencvtutorial.features.detection.grabcut

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.charlezz.opencvtutorial.BLACK
import com.charlezz.opencvtutorial.BLUE
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.toBitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import javax.inject.Inject

@HiltViewModel
class GrabCutViewModel @Inject constructor(
    private val app: Application,
    private val drawSelectionRGBUseCase: DrawSelectionRGBUseCase,
    private val grabCutRGBUseCase: GrabCutRGBUseCase
) : AndroidViewModel(app) {

    private val _srcBitmap: MutableStateFlow<Bitmap> by lazy {
        MutableStateFlow(
            BitmapFactory.decodeResource(
                app.resources,
                R.drawable.runa
            )
        )
    }
    val state = MutableStateFlow<State>(State.Loading)

    val srcBitmap get() = _srcBitmap

    private val rgbaSrcMat: Flow<Mat> by lazy {
        _srcBitmap.map { bmp ->
            Mat().apply {
                Utils.bitmapToMat(bmp, this)
            }
        }
    }

    val aspectRatio: Flow<String> by lazy {
        _srcBitmap.map { bmp ->
            "${bmp.width}:${bmp.height}"
        }
    }

    private val mask:Mat by lazy {
        Mat.zeros(
            _srcBitmap.value.height,
            _srcBitmap.value.width,
            CvType.CV_8U
        )
    }

    fun reset() {
        state.value = State.Initialize(srcBitmap.value)
        mask.setTo(Scalar(0.0))
    }

    fun drawSelection(rect: Rect) {
        viewModelScope.launch {
            rgbaSrcMat.collectLatest { src ->
                withContext(Dispatchers.Main) {
                    Imgproc.rectangle(
                        mask,
                        rect,
                        Scalar(Imgproc.GC_PR_FGD.toDouble()),
                        -1
                    )
                    state.value = State.DrawRect(
                        drawSelectionRGBUseCase(src, rect)
                            .toBitmap()
                    )
                }
            }
        }

    }

    fun grabCut(rect: Rect) {
        state.value = State.Loading
        viewModelScope.launch {
            rgbaSrcMat.collectLatest { src ->
                withContext(Dispatchers.IO) {
                    val rgbSrc = Mat()
                    Imgproc.cvtColor(
                        src,
                        rgbSrc,
                        Imgproc.COLOR_RGBA2RGB
                    )
                    state.value = State.GrabCut(
                        grabCutRGBUseCase(
                            rgbSrc,
                            mask,
                            rect
                        ).toBitmap()
                    )
                }
            }
        }
    }

    fun drawCircle(center: Point, radius: Int = 5) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (state.value is State.GrabCut ||
                    state.value is State.DrawCircle) {
                    Imgproc.circle(
                        mask,
                        center,
                        radius,
                        Scalar(Imgproc.GC_BGD.toDouble()),
                        -1
                    )
                    val bitmap = state.value.bitmap
                    val dst = Mat()
                    Utils.bitmapToMat(bitmap, dst)

                    Imgproc.cvtColor(
                        dst,
                        dst,
                        Imgproc.COLOR_RGBA2BGR
                    )
                    Imgproc.circle(
                        dst,
                        center,
                        radius,
                        BLUE,
                        -1
                    )
                    Imgproc.cvtColor(
                        dst,
                        dst,
                        Imgproc.COLOR_BGR2RGBA
                    )

                    state.value = State.DrawCircle(dst.toBitmap())
                }

            }
        }

    }

    sealed class State(val bitmap: Bitmap?) {
        class GrabCut(val bmp: Bitmap) : State(bmp)
        class DrawRect(val bmp: Bitmap) : State(bmp)
        class Initialize(val bmp: Bitmap) : State(bmp)
        object Loading : State(null)
        class DrawCircle(val bmp: Bitmap) : State(bmp)
    }
}