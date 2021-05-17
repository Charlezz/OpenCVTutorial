package com.charlezz.opencvtutorial.features.geometry

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.databinding.FragmentResizeBinding
import com.charlezz.opencvtutorial.databinding.FragmentWarpAffineBinding
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import timber.log.Timber
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@AndroidEntryPoint
class ResizeFragment : Fragment() {

    private var _binding: FragmentResizeBinding? = null
    private val binding: FragmentResizeBinding get() = _binding!!

    @Inject
    lateinit var bitmapUtil: BitmapUtil

    private val adapter: ArrayAdapter<String> by lazy {
        ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1
        ).apply {
            add("INTER_LINEAR")
            add("INTER_NEAREST")
            add("INTER_CUBIC")
            add("INTER_LANCZOS4")
        }
    }

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
        _binding = FragmentResizeBinding.inflate(
            inflater,
            container,
            false
        )

        binding.spinner.adapter = adapter
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        resizeImage()

        binding.btn.setOnClickListener {
            resizeImage()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun resizeImage() {
        val dst = Mat()
        val width = binding.width.text.toString().toDouble()
        val height = binding.height.text.toString().toDouble()
        val interpolation = when (binding.spinner.selectedItem) {
            "INTER_NEAREST" -> Imgproc.INTER_NEAREST
            "INTER_LINEAR" -> Imgproc.INTER_LINEAR
            "INTER_CUBIC" -> Imgproc.INTER_CUBIC
            "INTER_LANCZOS4" -> Imgproc.INTER_LANCZOS4
            else ->Imgproc.INTER_LINEAR
        }
        val processingTime = measureTimeMillis {
            Imgproc.resize(src, dst, Size(width, height), 0.0, 0.0, interpolation)
        }

        binding.time.text = "이미지 프로세싱 소요 시간:$processingTime"

        binding.imageView.setImageBitmap(
            bitmapUtil.bitmapFrom(
                dst
            )
        )
    }

}