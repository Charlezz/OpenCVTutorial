package com.charlezz.opencvtutorial.presentation.screen.basic

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.core.*
import org.opencv.imgproc.Imgproc

/**
 * @author soohwan.ok
 */
@Composable
fun LogicalOperationContent() {

    val image1 = Mat.zeros(300, 300, CvType.CV_8UC1).apply {
        Imgproc.rectangle(this, Point(100.0, 100.0), Point(250.0, 250.0), Scalar(255.0), -1)
    }
    val image2 = Mat.zeros(300, 300, CvType.CV_8UC1).apply {
        Imgproc.circle(this, Point(150.0, 150.0), 90, Scalar(255.0), -1)
    }

    val and = Mat()
    Core.bitwise_and(image1, image2, and)

    val or = Mat()
    Core.bitwise_or(image1, image2, or)

    val xor = Mat()
    Core.bitwise_xor(image1, image2, xor)

    val not = Mat()
    Core.bitwise_not(image1, not)

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                state = scrollState
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row{
            Text(
                modifier = Modifier.fillMaxWidth(0.5f),
                text = "A"
            )
            Text(
                text = "B"
            )
        }
        Row {
            Image(
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                bitmap = image1.toBitmap().asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )

            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                bitmap = image2.toBitmap().asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        }

        Row{
            Text(
                modifier = Modifier.fillMaxWidth(0.5f),
                text = "bitwise_and(A, B)"
            )
            Text(
                text = "bitwise_or(A, B)"
            )
        }

        Row {
            Image(
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                bitmap = and.toBitmap().asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )

            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                bitmap = or.toBitmap().asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        }

        Row{
            Text(
                modifier = Modifier.fillMaxWidth(0.5f),
                text = "bitwise_xor(A, B)"
            )
            Text(
                text = "bitwise_not(A)"
            )
        }

        Row {
            Image(
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                bitmap = xor.toBitmap().asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )

            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                bitmap = not.toBitmap().asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        }
    }
}