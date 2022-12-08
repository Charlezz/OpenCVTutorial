package com.charlezz.opencvtutorial.presentation.screen.extraction

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.charlezz.opencvtutorial.BLUE
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.RED
import com.charlezz.opencvtutorial.presentation.component.ImageCard
import com.charlezz.opencvtutorial.toBitmap
import com.charlezz.opencvtutorial.util.copyToCacheDir
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import org.opencv.objdetect.CascadeClassifier
import kotlin.math.roundToInt


@Composable
fun FaceDetectionContent() {
    val context = LocalContext.current
    val image by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.lenna)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }
    val faceModelName = "haarcascade_frontalface_alt.xml"
    val eyesModelName = "haarcascade_eye.xml"
    val faceModelFile = copyToCacheDir(context, faceModelName)
    val eyesModelFile = copyToCacheDir(context, eyesModelName)
    val faceCascade = CascadeClassifier().apply {
        load(faceModelFile.absolutePath)
    }
    val eyesCascade = CascadeClassifier().apply {
        load(eyesModelFile.absolutePath)
    }

    val grayImage = Mat().apply {
        Imgproc.cvtColor(image, this, Imgproc.COLOR_RGB2GRAY)
        Imgproc.equalizeHist(this, this)
    }

    val dstImage = Mat().apply {
        Core.copyTo(image, this, Mat())
    }
    // -- Detect faces
    val faces = MatOfRect()
    faceCascade.detectMultiScale(grayImage, faces)
    val listOfFaces: List<Rect> = faces.toList()
    for (face in listOfFaces) {
        val center = Point(
            (face.x + face.width / 2).toDouble(),
            (face.y + face.height / 2).toDouble()
        )
        Imgproc.ellipse(
            dstImage, center, Size((face.width / 2).toDouble(), (face.height / 2).toDouble()), 0.0, 0.0, 360.0,
            RED
        )
        val faceROI: Mat = grayImage.submat(face)
        // -- In each face, detect eyes
        val eyes = MatOfRect()
        eyesCascade.detectMultiScale(faceROI, eyes)
        val listOfEyes: List<Rect> = eyes.toList()
        for (eye in listOfEyes) {
            val eyeCenter = Point(
                (face.x + eye.x + eye.width / 2).toDouble(),
                (face.y + eye.y + eye.height / 2).toDouble()
            )
            val radius = ((eye.width + eye.height) * 0.25).roundToInt()
            Imgproc.circle(dstImage, eyeCenter, radius, BLUE, 4)
        }
    }
    LazyColumn {
        item {
            ImageCard(
                title = "Original Image",
                imageBitmap = image.toBitmap().asImageBitmap()
            )
            ImageCard(
                title = "Face Detection",
                imageBitmap = dstImage.toBitmap().asImageBitmap()
            )
        }
    }

}
