package com.charlezz.opencvtutorial.features.arithmetic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.databinding.FragmentAddOperationBinding
import com.charlezz.opencvtutorial.databinding.FragmentAddWeightedOperationBinding
import com.charlezz.opencvtutorial.databinding.FragmentBrightnessBinding
import com.charlezz.opencvtutorial.databinding.FragmentSubtractOperationBinding
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import javax.inject.Inject

@AndroidEntryPoint
class AddWeightedOperationFragment : Fragment() {

    @Inject
    lateinit var bitmapUtil: BitmapUtil

    private var _binding: FragmentAddWeightedOperationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddWeightedOperationBinding.inflate(inflater, container, false)
        return binding.root
    }

    val lenna: Mat by lazy {
        Utils.loadResource(
            requireContext(),
            R.drawable.lenna,
            Imgcodecs.IMREAD_GRAYSCALE
        )
    }

    val runa by lazy {
        Utils.loadResource(
            requireContext(),
            R.drawable.runa,
            Imgcodecs.IMREAD_GRAYSCALE
        )
    }

    val result = Mat()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lenna.setImageBitmap(bitmapUtil.bitmapFrom(lenna))
        binding.runa.setImageBitmap(bitmapUtil.bitmapFrom(runa))


        showImages()

        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                showImages()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

    }

    fun showImages() {
        val alpha = binding.seekbar.progress.toDouble() / binding.seekbar.max.toDouble()
        val beta = 1.0 - alpha
        Core.addWeighted(lenna, alpha, runa, beta, 0.0, result)
        binding.result.setImageBitmap(bitmapUtil.bitmapFrom(result))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}