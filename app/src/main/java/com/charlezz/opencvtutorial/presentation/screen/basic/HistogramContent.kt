package com.charlezz.opencvtutorial.presentation.screen.basic

import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.toBitmap
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.MatOfFloat
import org.opencv.core.MatOfInt
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc

private enum class Mode {
    ORIGINAL,
    NORMALIZED,
    EQUALIZED,
}

/**
 * @author soohwan.ok
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistogramContent() {
    val scrollState = rememberScrollState()

    var isColor by remember { mutableStateOf(false) }
    var mode by remember { mutableStateOf(Mode.ORIGINAL) }

    val context = LocalContext.current

    val src = Utils.loadResource(
        context,
        R.drawable.lenna,
        if (isColor) Imgcodecs.IMREAD_UNCHANGED else Imgcodecs.IMREAD_GRAYSCALE
    )

    val dst = Mat()

    when (mode) {
        Mode.ORIGINAL -> {
            Core.copyTo(src, dst, Mat())
        }
        Mode.NORMALIZED -> {
            Core.normalize(src, dst, 0.0, 255.0, Core.NORM_MINMAX)
        }
        Mode.EQUALIZED -> {
            if (isColor) {
                val srcYCrCB = Mat()
                Imgproc.cvtColor(src, srcYCrCB, Imgproc.COLOR_BGR2YCrCb)

                // 채널 분리
                val srcPlanes = ArrayList<Mat>()
                Core.split(srcYCrCB, srcPlanes)

                // Y 채널에 대해서만 히스토그램 평탄화 수행
                Imgproc.equalizeHist(srcPlanes[0], srcPlanes[0])

                // 다시 채널 합치기
                val dstYCrCb = Mat()
                Core.merge(srcPlanes, dstYCrCb)
                Imgproc.cvtColor(dstYCrCb, dst, Imgproc.COLOR_YCrCb2BGR)
            } else {
                Imgproc.equalizeHist(src, dst)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(
                state = scrollState
            ),
    ) {
        Row {
            Switch(checked = isColor, onCheckedChange = { isColor = it })
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                text = "Color"
            )
        }
        Row {
            RadioButton(selected = mode == Mode.ORIGINAL, onClick = { mode = Mode.ORIGINAL })
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                text = "Original"
            )
        }
        Row {
            RadioButton(
                selected = mode == Mode.NORMALIZED,
                onClick = { mode = Mode.NORMALIZED }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                text = "Normalized"
            )
        }
        Row() {
            RadioButton(
                selected = mode == Mode.EQUALIZED,
                onClick = { mode = Mode.EQUALIZED }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                text = "Equalized"
            )
        }
        Image(
            modifier = Modifier.fillMaxWidth(),
            bitmap = dst.toBitmap(Imgproc.COLOR_BGR2RGBA).asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        AndroidView(
            factory = { LineChart(it) },
            modifier = Modifier.fillMaxWidth().height(300.dp),
            update = { lineChart ->
                if(isColor){
                    drawColorGraph(lineChart, dst)
                }else{
                    drawGrayGraph(lineChart, dst)
                }
            }
        )
    }


}

private fun drawGrayGraph(lineChart: LineChart, src:Mat){
    val hist = Mat()
    Imgproc.calcHist(
        listOf(src),// 입력 영상
        MatOfInt(0),// 채널
        Mat(),// 마스크
        hist,// 계산된 히스토그램
        MatOfInt(256),// histSize: 히스토그램 각 차원의 크기를 나타내는 리스트(빈(bin)의 개수)
        MatOfFloat(0f, 255f) // ranges: 히스토그램 각 차원의 최솟값과 최댓값으로 구성
    )

    val histData = FloatArray(hist.total().toInt())
    hist.get(0, 0, histData)

    val barEntryList = ArrayList<Entry>()
    for (i in 0 until 256) {
        barEntryList.add(Entry(i.toFloat(), histData[i]))
    }
    val dataSet = LineDataSet(barEntryList, "Histogram")
    dataSet.color = Color.GRAY
    dataSet.valueTextColor = Color.GRAY
    lineChart.data = LineData(dataSet)
    lineChart.invalidate()
}

private fun drawColorGraph(lineChart: LineChart, src:Mat) {
    val bgrPlanes = ArrayList<Mat>()

    Core.split(src, bgrPlanes)

    /**
     * 파랑 채널에 대한 히스토그램 생성
     */
    val blueHist = Mat()

    Imgproc.calcHist(
        bgrPlanes,// 입력 영상
        MatOfInt(0),// 채널
        Mat(),// 마스크
        blueHist,// 계산된 히스토그램
        MatOfInt(256),// histSize: 히스토그램 각 차원의 크기를 나타내는 리스트(빈(bin)의 개수)
        MatOfFloat(0f, 255f) // ranges: 히스토그램 각 차원의 최솟값과 최댓값으로 구성
    )

    val blueHistData = FloatArray(blueHist.total().toInt())
    blueHist.get(0, 0, blueHistData)

    val blueEntryList = ArrayList<Entry>()

    for (i in 0 until 256) {
        blueEntryList.add(Entry(i.toFloat(), blueHistData[i]))
    }
    val blueDataSet = LineDataSet(blueEntryList, "Blue")
    blueDataSet.color = Color.BLUE
    blueDataSet.valueTextColor = Color.BLUE

    /**
     * 녹색 채널에 대한 히스토그램 생성
     */
    val greenHist = Mat()

    Imgproc.calcHist(
        bgrPlanes,
        MatOfInt(1),
        Mat(),
        greenHist,
        MatOfInt(256),
        MatOfFloat(0f, 255f)
    )

    val greenHistData = FloatArray(greenHist.total().toInt())
    greenHist.get(0, 0, greenHistData)

    val greenEntryList = ArrayList<Entry>()
    for (i in 0 until 256) {
        greenEntryList.add(Entry(i.toFloat(), greenHistData[i]))
    }

    val greenDataSet = LineDataSet(greenEntryList, "Green")
    greenDataSet.color = Color.GREEN
    greenDataSet.valueTextColor = Color.GREEN

    /**
     * 빨강 채널에 대한 히스토그램 생성
     */

    val redHist = Mat()
    Imgproc.calcHist(
        bgrPlanes,// 입력 영상
        MatOfInt(2),// 채널
        Mat(),// 마스크
        redHist,// 계산된 히스토그램
        MatOfInt(256),// histSize: 히스토그램 각 차원의 크기를 나타내는 리스트(빈(bin)의 개수)
        MatOfFloat(0f, 255f) // ranges: 히스토그램 각 차원의 최솟값과 최댓값으로 구성

    )

    val redHistData = FloatArray(redHist.total().toInt())
    redHist.get(0, 0, redHistData)

    val redEntryList = ArrayList<Entry>()
    for (i in 0 until 256) {
        redEntryList.add(Entry(i.toFloat(), redHistData[i]))
    }
    val redDataSet = LineDataSet(redEntryList, "Red")
    blueDataSet.color = Color.RED
    blueDataSet.valueTextColor = Color.RED

    lineChart.data = LineData(blueDataSet, greenDataSet, redDataSet)
    lineChart.invalidate()
}