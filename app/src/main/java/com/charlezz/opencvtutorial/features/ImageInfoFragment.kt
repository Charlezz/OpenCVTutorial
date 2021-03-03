package com.charlezz.opencvtutorial.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.CacheUtil
import com.charlezz.opencvtutorial.databinding.FragmentImageInfoBinding
import com.charlezz.pickle.SingleConfig
import com.charlezz.pickle.getPickleForSingle
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.imgcodecs.Imgcodecs
import java.lang.StringBuilder
import javax.inject.Inject

@AndroidEntryPoint
class ImageInfoFragment : Fragment() {

    @Inject
    lateinit var cacheUtil: CacheUtil

    @Inject
    lateinit var bitmapUtil: BitmapUtil

    private var _binding: FragmentImageInfoBinding? = null
    private val binding get() = _binding!!

    private val pickle = getPickleForSingle { media ->
        media?.getUri()?.let { uri ->
            val file = cacheUtil.copyFrom(uri)
            val mat = Imgcodecs.imread(file.absolutePath)
            val bitmap = bitmapUtil.bitmapFrom(mat)
            binding.image.setImageBitmap(bitmap)

            val info = StringBuilder()
                .appendLine("width = ${mat.width()}")
                .appendLine("height = ${mat.height()}")
                .appendLine("pixels = ${mat.total()}")
                .appendLine("type = ${mat.type()}")
                .toString()

            binding.info.text = info
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSelect.setOnClickListener {
            pickle.launch(SingleConfig.default)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}