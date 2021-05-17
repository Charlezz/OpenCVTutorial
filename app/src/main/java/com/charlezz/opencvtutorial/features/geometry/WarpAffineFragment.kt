package com.charlezz.opencvtutorial.features.geometry

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.databinding.FragmentWarpAffineBinding
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.Utils
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.MatOfFloat
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class WarpAffineFragment : Fragment() {

    private var _binding: FragmentWarpAffineBinding? = null
    private val binding: FragmentWarpAffineBinding get() = _binding!!

    @Inject
    lateinit var bitmapUtil: BitmapUtil

    private val src: Mat by lazy {
        Utils.loadResource(
            requireContext(),
            R.drawable.runa
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWarpAffineBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.btn.setOnClickListener { transform() }
        transform()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun transform() {

        val m = Mat(2, 3, CvType.CV_32F)

        m.put(
            0, 0,
            floatArrayOf(
                binding.element1.text.toString().toFloat(),
                binding.element2.text.toString().toFloat(),
                binding.element3.text.toString().toFloat(),
                binding.element4.text.toString().toFloat(),
                binding.element5.text.toString().toFloat(),
                binding.element6.text.toString().toFloat(),
            )
        )

        val dst = Mat()
        Imgproc.warpAffine(
            src,
            dst,
            m,
            Size(0.0, 0.0),
        )

        bitmapUtil.bitmapFrom(dst)?.let {
            binding.imageView.setImageBitmap(it)
        }
    }


}