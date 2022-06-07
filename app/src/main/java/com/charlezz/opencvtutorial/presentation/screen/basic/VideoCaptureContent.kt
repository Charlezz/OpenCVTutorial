package com.charlezz.opencvtutorial.presentation.screen.basic

import android.content.Context
import android.content.res.AssetManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.charlezz.opencvtutorial.toBitmap
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.videoio.VideoCapture
import org.opencv.videoio.Videoio
import timber.log.Timber
import java.io.*

/**
 * @author soohwan.ok
 */
@Composable
fun VideoCaptureContent() {
    val context = LocalContext.current
    val assetName = "sample.mp4"
    val target = File(context.filesDir, assetName)
    copyAsset(context = context, assetName = assetName, target = target)
    val videoCapture = VideoCapture()
    val isOpened = videoCapture.open(target.absolutePath)
    val composableScope = rememberCoroutineScope()
    var mat by remember { mutableStateOf(Mat.zeros(1, 1, CvType.CV_8UC3)) }
    if (isOpened) {

        LaunchedEffect(key1 = Unit) {
            composableScope.launch {
                val fps = videoCapture.get(Videoio.CAP_PROP_FPS)
                val delayDuration = if (fps == 0.0) {
                    33L //fps값을 못 얻어오는 경우 33으로 딜레이 고정
                } else {
                    (1000.0 / fps).toLong()
                }
                var newMat = Mat()
                while (videoCapture.read(newMat)) {
                    Timber.d("read()")
                    mat = newMat
                    delay(delayDuration)
                    newMat = Mat()
                }
                videoCapture.release()
                Toast.makeText(context, "영상 종료", Toast.LENGTH_SHORT).show()
            }
        }
        Image(
            modifier = Modifier.fillMaxSize(),
            bitmap = mat.toBitmap().asImageBitmap(),
            contentDescription = null
        )
    } else {
        Toast.makeText(context, "Failed to open", Toast.LENGTH_SHORT).show()
    }

}

@Suppress("SameParameterValue")
private fun copyAsset(context: Context, assetName: String, target: File) {
    if (!target.exists()) {
        val assetManager: AssetManager = context.assets
        val inputStream = assetManager.open(assetName)
        val fileOutputStream = FileOutputStream(target)
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