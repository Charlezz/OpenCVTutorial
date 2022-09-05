package com.charlezz.opencvtutorial.presentation.screen.extraction

import android.content.res.Configuration
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.charlezz.opencvtutorial.*
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.presentation.component.ImageCard
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Point
import org.opencv.imgproc.Imgproc
import kotlin.math.round

private enum class Shapes(@DrawableRes val id: Int) {
    SPADE(R.drawable.spades),
    STAR(R.drawable.star),
    CLOVER(R.drawable.clover),
    TRIANGLE(R.drawable.triangle),
    HEART(R.drawable.heart)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MatchShapesContent() {
    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.symbols)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }
    var currentShape by remember { mutableStateOf(Shapes.SPADE) }
    val objBitmap = BitmapFactory.decodeResource(context.resources, currentShape.id)
    val objSrc = Mat() // 검출하고자 하는 객체 이미지
    Utils.bitmapToMat(objBitmap, objSrc)
    Imgproc.cvtColor(objSrc, objSrc, Imgproc.COLOR_RGB2GRAY)

    val binObjSrc = Mat()  // 이진화된 객체 이미지
    Imgproc.threshold(objSrc, binObjSrc, 128.0, 255.0, Imgproc.THRESH_BINARY_INV)
    val objContours = ArrayList<MatOfPoint>() // 객체 이미지의 윤곽선 정보
    val objHierarchy = Mat()
    Imgproc.findContours(
        binObjSrc,
        objContours,
        objHierarchy,
        Imgproc.RETR_EXTERNAL,
        Imgproc.CHAIN_APPROX_NONE
    )
    val objPts = objContours.firstOrNull() ?: return //윤곽선을 못찾았으면 종료

    val grayBgSrc = Mat() // 검출하고자 하는 객체가 포함된 이미지
    Imgproc.cvtColor(src, grayBgSrc, Imgproc.COLOR_BGR2GRAY)

    val binBgSrc = Mat()  // 이진화된 이미지
    Imgproc.threshold(grayBgSrc, binBgSrc, 128.0, 255.0, Imgproc.THRESH_BINARY_INV)

    val bgContours = ArrayList<MatOfPoint>() // 이미지의 윤곽선 정보
    val bgHierarchy = Mat()

    Imgproc.findContours(
        binBgSrc,
        bgContours,
        bgHierarchy,
        Imgproc.RETR_EXTERNAL,
        Imgproc.CHAIN_APPROX_NONE
    )

    val dst = Mat().apply { src.copyTo(this) }
    bgContours.forEachIndexed { index, pts ->
        if (Imgproc.contourArea(pts) > 1000) {
            val rect = Imgproc.boundingRect(pts)  // 검출한 객체의 감싸는 사각형
            Imgproc.rectangle(dst, rect, BLUE, 1) // 파랑색 사각형으로 객체를 감싼다.

            // matchShape는 두 윤곽선의 사이의 거리(차이)를 반환
            val dist = Imgproc.matchShapes(
                objPts, // 찾고자 하는 객체의 윤곽선
                pts, // 검출한 객체의 윤곽선
                Imgproc.CONTOURS_MATCH_I3, // 매칭 방식
                0.0 // (사용되지 않음)
            )

            // 0.1보다 낮은 차이를 보여줄 때 객체를 찾았다고 판단한다.
            val found = dist < 0.06
            if (found) {
                // 찾은 객체는 빨간 선으로 두텁께 다시 그린다
                Imgproc.rectangle(dst, rect, RED, 2)
            }

            // dist값을 출력함
            putTextOnTop(
                img = dst,
                contour = pts,
                text = "${round(dist * 100.0) / 100}",
                textColor = if (found) RED else BLUE
            )

        }
    }

    LazyColumn {
        stickyHeader {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(state = rememberScrollState()),
                horizontalArrangement = Arrangement.Center
            ) {
                Shapes.values().forEach { shape ->
                    Spacer(modifier = Modifier.requiredSize(10.dp))
                    OutlinedCard(
                        modifier = Modifier
                            .clickable { currentShape = shape }
                            .then(
                                if (shape == currentShape) {
                                    Modifier.border(width = 1.dp, color = Color.Red, RoundedCornerShape(10.dp))
                                } else {
                                    Modifier
                                }
                            )

                    ) {
                        Image(
                            modifier = Modifier.requiredSize(50.dp),
                            painter = painterResource(id = shape.id),
                            contentDescription = null
                        )
                    }
                }
            }
        }
        item {
            ImageCard(
                title = "",
                imageBitmap = dst.toBitmap().asImageBitmap()
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MatchShapesContentPreview() {
    MatchShapesContent()
}