package com.charlezz.opencvtutorial.presentation.screen.geometry

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.presentation.component.ImageCard
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc

/**
 * @author soohwan.ok
 */
@Composable
fun PerspectiveTransformContent() {
    val context = LocalContext.current
    // A4 용지 크기: 210x297cm
    val width = 210.0
    val height = 297.0

    val src = Utils.loadResource(context, R.drawable.scanned)
    Imgproc.resize(src, src, Size(600.0, 800.0))

    val pt1 = Point(58.0, 130.0)
    val pt2 = Point(420.0, 130.0)
    val pt3 = Point(88.0, 710.0)
    val pt4 = Point(570.0, 635.0)

    Imgproc.circle(src, pt1, 10, Scalar(0.0, 0.0, 255.0), -1)
    Imgproc.circle(src, pt2, 10, Scalar(0.0, 255.0, 0.0), -1)
    Imgproc.circle(src, pt3, 10, Scalar(255.0, 0.0, 0.0), -1)
    Imgproc.circle(src, pt4, 10, Scalar(0.0, 255.0, 255.0), -1)

    val srcQuad = MatOfPoint2f(
        pt1,
        pt2,
        pt3,
        pt4
    )
    val scaleFactor = 2.857
    val dstQuad = MatOfPoint2f(
        Point(0.0, 0.0),
        Point(width*scaleFactor - 1, 0.0),
        Point(0.0, height*scaleFactor - 1),
        Point(width*scaleFactor - 1, height*scaleFactor - 1),
    )

    val perspectiveTransform = Imgproc.getPerspectiveTransform(srcQuad, dstQuad)

    var dst = Mat()
    Imgproc.warpPerspective(src, dst, perspectiveTransform, Size(0.0, 0.0))

    PerspectiveTransformContent(
        src = src.toBitmap(Imgproc.COLOR_BGR2RGB).asImageBitmap(),
        transformed = dst.toBitmap(Imgproc.COLOR_BGR2RGB).asImageBitmap()
    )

}

@Composable
private fun PerspectiveTransformContent(
    src: ImageBitmap,
    transformed: ImageBitmap,
) {
    LazyColumn {
        item {
            ImageCard(
                title = "Original",
                imageBitmap = src
            )
        }
        item {
            ImageCard(
                title = "Perspective Transform",
                imageBitmap = transformed
            )
        }
    }
}

@Preview
@Composable
private fun PerspectiveTransformContentPreview() {
    PerspectiveTransformContent(
        src = ImageBitmap.imageResource(id = R.drawable.runa),
        transformed = ImageBitmap.imageResource(id = R.drawable.runa),
    )
}