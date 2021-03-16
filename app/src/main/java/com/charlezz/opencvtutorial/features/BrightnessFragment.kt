package com.charlezz.opencvtutorial.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.databinding.FragmentBrightnessBinding
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgcodecs.Imgcodecs
import javax.inject.Inject

@AndroidEntryPoint
class BrightnessFragment : Fragment() {
    private var _binding: FragmentBrightnessBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var bitmapUtil: BitmapUtil

    private val src: Mat by lazy {
        Utils.loadResource(
            requireContext(),
            R.drawable.lenna,
            Imgcodecs.IMREAD_GRAYSCALE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBrightnessBinding.inflate(inflater, container, false)
        val brightness = binding.seekbar.progress - 50
        show(calcBrightness(src, brightness), binding.image)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val brightness = binding.seekbar.progress - 50
                show(calcBrightness(src, brightness), binding.image)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun calcBrightness(src: Mat, additionalBrightness: Int): Mat {
        // 밝기를 스칼라값으로 치환
        val src2 = Scalar.all(additionalBrightness.toDouble())
        val dst = Mat()
        Core.add(src, src2, dst)
        return dst
    }

    private fun show(src: Mat, imageView: ImageView) {
        val dst = bitmapUtil.bitmapFrom(src)
        imageView.setImageBitmap(dst)
    }
}