package com.charlezz.opencvtutorial.presentation.screen.geometry

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.presentation.component.ImageCard
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.core.*
import org.opencv.imgproc.Imgproc

/**
 * @author soohwan.ok
 */
@Composable
fun AffineTransformContent() {
    val redDotPt = Point(150.0, 150.0)
    val blueDotPt = Point(150.0, 850.0)
    val greenDotPt = Point(850.0, 150.0)

    val pt1 = MatOfPoint2f(
        redDotPt,
        blueDotPt,
        greenDotPt
    )

    val pt2 = MatOfPoint2f(
        Point(150.0, 150.0),
        Point(150.0, 850.0),
        Point(850.0, 500.0)
    )

    val src = getChessImage(
        redDotPt = redDotPt,
        blueDotPt = blueDotPt,
        greenDotPt = greenDotPt
    )

    val affineTransform = Imgproc.getAffineTransform(pt1,pt2)
    val transformed = Mat()
    Imgproc.warpAffine(src, transformed, affineTransform, Size(0.0,0.0))

    AffineTransformContent(
        src = src.toBitmap(Imgproc.COLOR_BGR2RGB).asImageBitmap(),
        transformed = transformed.toBitmap(Imgproc.COLOR_BGR2RGB).asImageBitmap(),
    )
}


@Composable
private fun AffineTransformContent(
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
                title = "Affine Transform",
                imageBitmap = transformed
            )
        }
    }
}

@Preview
@Composable
private fun AffineTransformContentPreview() {
    AffineTransformContent(
        src = ImageBitmap.imageResource(id = R.drawable.runa),
        transformed = ImageBitmap.imageResource(id = R.drawable.runa),
    )
}

private fun getChessImage(
    redDotPt:Point,
    blueDotPt:Point,
    greenDotPt:Point,
): Mat {
    val width = 1000
    val src = Mat(width, width, CvType.CV_8UC3)
    src.setTo(Scalar(128.0, 128.0, 255.0))//회색조 배경
    val columnCount = 8
    val margin = 150
    val columnSpace: Double = ((width - 2 * margin) / columnCount).toDouble()

    for (row in 0 until columnCount) {

        for (col in 0 until columnCount) {
            val x: Double = row * columnSpace + margin
            val y: Double = col * columnSpace + margin


            val color = if (row % 2 == 0) {
                if (col % 2 == 0) {
                    255.0
                } else {
                    0.0
                }
            } else {
                if (col % 2 == 0) {
                    0.0
                } else {
                    255.0
                }
            }

            Imgproc.rectangle(
                src,
                Point(x, y),
                Point(x + columnSpace, y + columnSpace),
                Scalar(color, color, color),
                -1
            )
        }
    }

    Imgproc.circle(src, redDotPt, 10, Scalar(0.0, 0.0, 255.0), -1)
    Imgproc.circle(src, blueDotPt, 10, Scalar(255.0, 0.0, 0.0), -1)
    Imgproc.circle(src, greenDotPt, 10, Scalar(0.0, 255.0, 0.0), -1)

    return src
}