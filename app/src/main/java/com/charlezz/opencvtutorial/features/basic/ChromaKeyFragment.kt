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
import com.charlezz.opencvtutorial.databinding.FragmentChromaKeyBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import org.opencv.videoio.VideoCapture
import org.opencv.videoio.Videoio
import java.io.*
import javax.inject.Inject

@AndroidEntryPoint
class ChromaKeyFragment : Fragment() {

    @Inject
    lateinit var bitmapUtil: BitmapUtil

    private val chromaKeyVideo: File by lazy {
        File(requireContext().filesDir, "chroma_key_monitor.mp4")
    }

    private val video: File by lazy {
        File(requireContext().filesDir, "sample.mp4")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        copyAssets()
    }

    private val vcChromaKeyVideo by lazy { VideoCapture(chromaKeyVideo.absolutePath) }
    private val vcVideo by lazy { VideoCapture(video.absolutePath) }


    private var _binding: FragmentChromaKeyBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChromaKeyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (vcChromaKeyVideo.isOpened && vcVideo.isOpened ) {
            val fps = vcChromaKeyVideo.get(Videoio.CAP_PROP_FPS)
            val delayDuration = if (fps == 0.0) {
                33L //fps값을 못 얻어오는 경우 33으로 딜레이 고정
            } else {
                (1000.0 / fps).toLong()
            }
            val src = Mat()
            val srcHSV = Mat()
            val mask = Mat()

            val videoMat = Mat()
            lifecycleScope.launch {
                while (vcChromaKeyVideo.read(src)) {
                    if (binding.composite.isChecked) {
                        if(!vcVideo.read(videoMat)){
                            return@launch
                        }

                        Imgproc.cvtColor(src, srcHSV, Imgproc.COLOR_RGB2HSV)

                        val lowerb = Scalar(50.0, 150.0, .0)
                        val upperb = Scalar(80.0, 255.0, 255.0)

                        Core.inRange(srcHSV, lowerb, upperb, mask)
                        Core.copyTo(videoMat, src, mask)

                    }
                    Imgproc.cvtColor(src, src, Imgproc.COLOR_RGB2BGR)
                    binding.imageView.setImageBitmap(bitmapUtil.bitmapFrom(src))
                    delay(delayDuration)
                }
                vcChromaKeyVideo.release()
                vcVideo.release()
                Toast.makeText(requireContext(), "영상 종료", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Failed to open", Toast.LENGTH_SHORT).show()
        }

    }

    private fun copyAssets() {
        val assetManager: AssetManager = requireContext().assets

        if (!chromaKeyVideo.exists()) {
            val fileOutputStream = FileOutputStream(chromaKeyVideo)
            copyFile(assetManager.open(chromaKeyVideo.name), fileOutputStream)
        }

        if (!video.exists()) {
            val fileOutputStream = FileOutputStream(video)
            copyFile(assetManager.open(video.name), fileOutputStream)
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