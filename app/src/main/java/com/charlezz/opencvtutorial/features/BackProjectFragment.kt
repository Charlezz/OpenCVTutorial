package com.charlezz.opencvtutorial.features

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.databinding.FragmentBackProjectBinding
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import javax.inject.Inject

@AndroidEntryPoint
class BackProjectFragment : Fragment(){

    @Inject
    lateinit var bitmapUtils: BitmapUtil

    private var _binding:FragmentBackProjectBinding? = null
    private val binding get() = _binding!!

    var initX = 0F
    var initY = 0F
    private lateinit var src:Mat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBackProjectBinding.inflate(inflater, container, false)
        return binding.root
    }
    val mask = Mat()
    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.image.post {
            src = Utils.loadResource(requireContext(), R.drawable.runa)
            Imgproc.resize(src, src, Size(binding.image.measuredWidth.toDouble(), binding.image.measuredHeight.toDouble()))
            binding.image.setImageBitmap(bitmapUtils.bitmapFrom(src))
            binding.result.setImageBitmap(null)
        }

        binding.reset.setOnClickListener {
            binding.image.setImageBitmap(bitmapUtils.bitmapFrom(src))
            binding.result.setImageBitmap(null)
        }

        binding.image.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initX = event.x
                    initY = event.y
                }

                MotionEvent.ACTION_MOVE -> {
                    val dst = Mat()
                    Core.copyTo(src, dst, mask)
                    Imgproc.rectangle(
                        dst,
                        Point(initX.toDouble(), initY.toDouble()),
                        Point(event.x.toDouble(), event.y.toDouble()),
                        Scalar(0.0, 0.0, 255.0)
                    )
                    binding.image.setImageBitmap(bitmapUtils.bitmapFrom(dst))
                }
                MotionEvent.ACTION_UP->{
                    val srcWithRoi = Mat(src, Rect(Point(initX.toDouble(), initY.toDouble()), Point(event.x.toDouble(), event.y.toDouble())))
                    val srcYCrCb = Mat()
                    Imgproc.cvtColor(srcWithRoi, srcYCrCb, Imgproc.COLOR_BGR2YCrCb)

                    val channels = MatOfInt(1,2)
                    val ranges = MatOfFloat(0f,256f,0f,256f)
                    val hist = Mat()

                    Imgproc.calcHist(listOf(srcWithRoi), channels, mask, hist, MatOfInt(128,128), ranges)

                    val backProject = Mat()
                    Imgproc.calcBackProject(
                        listOf(src),
                        channels,
                        hist,
                        backProject,
                        ranges,
                        1.0
                    )

                    binding.result.setImageBitmap(bitmapUtils.bitmapFrom(backProject))
                }
            }
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}