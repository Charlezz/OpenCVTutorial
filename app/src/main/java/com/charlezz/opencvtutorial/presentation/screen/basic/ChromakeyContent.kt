package com.charlezz.opencvtutorial.presentation.screen.basic

import android.content.Context
import android.content.res.AssetManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.charlezz.opencvtutorial.toBitmap
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import org.opencv.videoio.VideoCapture
import org.opencv.videoio.Videoio
import java.io.*

/**
 * @author soohwan.ok
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChromakeyContent() {
    val context = LocalContext.current
    val chromakeyVideoName = "chroma_key_monitor.mp4"
    val videoName = "sample.mp4"
    val chromakeyVideoFile = File(context.filesDir, chromakeyVideoName)
    val videoFile = File(context.filesDir, videoName)
    copyAssets(
        context = context,
        chromakeyVideoName = chromakeyVideoName,
        chromakeyVideoFile = chromakeyVideoFile,
        videoName = videoName,
        videoFile = videoFile
    )

    val vcChromaKeyVideo by lazy { VideoCapture(chromakeyVideoFile.absolutePath) }
    val vcVideo by lazy { VideoCapture(videoFile.absolutePath) }

    if (!(vcChromaKeyVideo.isOpened && vcVideo.isOpened)) {
        return
    }
    var isChecked by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()
    val fps = vcChromaKeyVideo.get(Videoio.CAP_PROP_FPS)
    val delayDuration = if (fps == 0.0) {
        33L //fps값을 못 얻어오는 경우 33으로 딜레이 고정
    } else {
        (1000.0 / fps).toLong()
    }
    val src by remember { mutableStateOf(Mat(1, 1, CvType.CV_8UC3)) }
    var dst by remember { mutableStateOf(src) }
    val srcHSV = Mat()
    val mask = Mat()
    val videoMat = Mat()
    LaunchedEffect(key1 = Unit) {
        scope.launch {
            while (vcChromaKeyVideo.read(src)) {
                if (isChecked) {
                    if (!vcVideo.read(videoMat)) {
                        return@launch
                    }

                    Imgproc.cvtColor(src, srcHSV, Imgproc.COLOR_RGB2HSV)

                    val lowerb = Scalar(50.0, 150.0, .0)
                    val upperb = Scalar(80.0, 255.0, 255.0)

                    Core.inRange(srcHSV, lowerb, upperb, mask)
                    Core.copyTo(videoMat, src, mask)

                }
                val newDst = Mat()
                src.copyTo(newDst)
                dst = newDst
                delay(delayDuration)
            }
            vcChromaKeyVideo.release()
            vcVideo.release()
            Toast.makeText(context, "End of the video", Toast.LENGTH_SHORT).show()
        }
    }

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                state = scrollState
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            bitmap = dst.toBitmap().asImageBitmap(),
            contentDescription = null
        )
        Switch(checked = isChecked, onCheckedChange = { isChecked = it })
    }


}

private fun copyAssets(
    context: Context,
    chromakeyVideoName: String,
    chromakeyVideoFile: File,
    videoName: String,
    videoFile: File,

    ) {
    val assetManager: AssetManager = context.assets

    if (!chromakeyVideoFile.exists()) {
        val fileOutputStream = FileOutputStream(chromakeyVideoFile)
        copyFile(assetManager.open(chromakeyVideoName), fileOutputStream)
    }

    if (!videoFile.exists()) {
        val fileOutputStream = FileOutputStream(videoFile)
        copyFile(assetManager.open(videoName), fileOutputStream)
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