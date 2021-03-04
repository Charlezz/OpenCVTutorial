package com.charlezz.opencvtutorial.features

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.CacheUtil
import com.charlezz.opencvtutorial.ImageItem
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.databinding.FragmentDrawingBinding
import com.charlezz.opencvtutorial.databinding.FragmentImageChannelBinding
import com.charlezz.pickle.SingleConfig
import com.charlezz.pickle.getPickleForSingle
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.sqrt

@AndroidEntryPoint
class DrawingFragment : Fragment() {
    @Inject
    lateinit var cacheUtil: CacheUtil

    @Inject
    lateinit var bitmapUtil: BitmapUtil

    private var _binding: FragmentDrawingBinding? = null
    private val binding get() = _binding!!

    var initX = 0F
    var initY = 0F
    lateinit var mat: Mat

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDrawingBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.canvas.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initX = event.x
                    initY = event.y
                }

                MotionEvent.ACTION_MOVE -> {
                    mat = Mat.zeros(
                        binding.canvas.measuredHeight,
                        binding.canvas.measuredWidth,
                        CvType.CV_8UC3
                    )

                    when (binding.radioGroup.checkedRadioButtonId) {
                        R.id.line -> {
                            Imgproc.line(
                                mat,
                                Point(initX.toDouble(), initY.toDouble()),
                                Point(event.x.toDouble(), event.y.toDouble()),
                                Scalar(255.0, 0.0, 0.0)
                            )
                        }
                        R.id.rectangle -> {
                            Imgproc.rectangle(
                                mat,
                                Point(initX.toDouble(), initY.toDouble()),
                                Point(event.x.toDouble(), event.y.toDouble()),
                                Scalar(255.0, 0.0, 0.0)
                            )
                        }
                        R.id.circle -> {
                            val absX = abs(initX - event.x)
                            val absY = abs(initY - event.y)

                            Imgproc.circle(
                                mat,
                                Point(initX.toDouble(), initY.toDouble()),
                                sqrt(absX * absX + absY * absY).toInt(),
                                Scalar(255.0, 0.0, 0.0)
                            )
                        }
                    }
                    val bmp = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888)
                    Utils.matToBitmap(mat, bmp)
                    binding.canvas.setImageBitmap(bmp)
                }
            }
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}