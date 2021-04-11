package com.charlezz.opencvtutorial.features.basic

import android.content.res.AssetManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.databinding.FragmentVideoCaptureBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.opencv.core.Mat
import org.opencv.videoio.VideoCapture
import org.opencv.videoio.Videoio
import java.io.*
import javax.inject.Inject

@AndroidEntryPoint
class VideoCaptureFragment : Fragment() {

    @Inject
    lateinit var bitmapUtil: BitmapUtil

    private var _binding: FragmentVideoCaptureBinding? = null
    private val binding get() = _binding!!

    private val file: File by lazy {
        File(requireContext().filesDir, "sample.mp4")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        copyAssets()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoCaptureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videoCapture = VideoCapture()
        val isOpened = videoCapture.open(file.absolutePath)
        if (isOpened) {
            val mat = Mat()
            lifecycleScope.launch {
                val fps = videoCapture.get(Videoio.CAP_PROP_FPS)
                val delayDuration = if (fps == 0.0) {
                    33L //fps값을 못 얻어오는 경우 33으로 딜레이 고정
                } else {
                    (1000.0 / fps).toLong()

                }

                while (videoCapture.read(mat)) {
                    binding.image.setImageBitmap(bitmapUtil.bitmapFrom(mat))
                    delay(delayDuration)
                }
                videoCapture.release()
                Toast.makeText(requireContext(), "영상 종료", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(requireContext(), "Failed to open", Toast.LENGTH_SHORT).show()
        }

    }

    private fun copyAssets() {
        val assetManager: AssetManager = requireContext().assets
        val inputStream = assetManager.open("sample.mp4")
        if (!file.exists()) {
            val fileOutputStream = FileOutputStream(file)
            copyFile(inputStream, fileOutputStream)
        }
    }

    @Throws(IOException::class)
    private fun copyFile(inputStream: InputStream, outputStream: OutputStream) {
        val buffer = ByteArray(1024)
        var read: Int
        while (inputStream.read(buffer).also { read = it } != -1) {
            outputStream.write(buffer, 0, read)
        }
    }

}