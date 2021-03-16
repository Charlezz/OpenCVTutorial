package com.charlezz.opencvtutorial.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.CacheUtil
import com.charlezz.opencvtutorial.ImageItem
import com.charlezz.opencvtutorial.databinding.FragmentImageChannelBinding
import com.charlezz.opencvtutorial.databinding.FragmentImageInfoBinding
import com.charlezz.pickle.SingleConfig
import com.charlezz.pickle.getPickleForSingle
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import java.lang.StringBuilder
import javax.inject.Inject

@AndroidEntryPoint
class ImageChannelFragment : Fragment() {

    @Inject
    lateinit var cacheUtil: CacheUtil

    @Inject
    lateinit var bitmapUtil: BitmapUtil

    @Inject
    lateinit var adapter: GroupieAdapter

    private var _binding: FragmentImageChannelBinding? = null
    private val binding get() = _binding!!

    private val pickle = getPickleForSingle { media ->
        media?.getUri()?.let { uri ->
            adapter.clear()
            val file = cacheUtil.copyFrom(uri)
            val mat = Imgcodecs.imread(file.absolutePath, Imgcodecs.IMREAD_COLOR)
            val channel = ArrayList<Mat>(3)
            Core.split(mat, channel)

            channel.forEach { mat ->
                bitmapUtil.bitmapFrom(mat)?.let { bitmap ->
                    adapter.add(ImageItem(bitmap))
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageChannelBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
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