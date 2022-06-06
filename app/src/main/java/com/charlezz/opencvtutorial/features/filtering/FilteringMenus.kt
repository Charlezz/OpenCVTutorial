package com.charlezz.opencvtutorial.features.filtering

import com.charlezz.opencvtutorial.Direction
import com.charlezz.opencvtutorial.Menu
import com.charlezz.opencvtutorial.MenuFragmentDirections
import com.charlezz.opencvtutorial.R
import org.opencv.imgcodecs.Imgcodecs

sealed class FilteringMenus {

    object Filter2D : Menu(

        Direction.from(

            MenuFragmentDirections.actionMenuFragmentToSingleProcessFragment(
                processor = Filter2DProcessor(),
                image =R.drawable.lenna,
                imreadMode =Imgcodecs.IMREAD_GRAYSCALE
            ),"기본적인 필터 적용하기",
        ),
        0
    )

    object Blur : Menu(
        Direction.from(
            MenuFragmentDirections.actionMenuFragmentToSingleProcessFragment(
                processor = BlurProcessor(),
                image = R.drawable.lenna,
                imreadMode = Imgcodecs.IMREAD_GRAYSCALE
            ),"블러 적용하기",
        ),
        1
    )

    object GaussianBlur : Menu(

        Direction.from(
            MenuFragmentDirections.actionMenuFragmentToSingleProcessFragment(
                GaussianBlurProcessor(),
                Imgcodecs.IMREAD_GRAYSCALE,
                R.drawable.lenna,
            ),"가우시안 블러 적용하기",
        ),
        2
    )

    object Sharpening : Menu(

        Direction.from(
            MenuFragmentDirections.actionMenuFragmentToSingleProcessFragment(
                SharpeningProcessor(),
                Imgcodecs.IMREAD_GRAYSCALE,
                R.drawable.lenna,
            ),"언샤프 필터 적용하기",
        ),
        3
    )

    object MedianFilter: Menu(

        Direction.from(
            MenuFragmentDirections.actionMenuFragmentToSingleProcessFragment(
                MedianFilterProcessor(),
                Imgcodecs.IMREAD_GRAYSCALE,
                R.drawable.lenna_salt_pepper,
            ),"미디언 필터 적용하기",
        ),
        4
    )

    object BilateralFilter: Menu(

        Direction.from(
            MenuFragmentDirections.actionMenuFragmentToSingleProcessFragment(
                MedianFilterProcessor(),
                Imgcodecs.IMREAD_COLOR,
                R.drawable.lenna,
            ),"양방향 필터 적용하기",
        ),
        5
    )

    object CartoonFilter: Menu(

        Direction.from(
            MenuFragmentDirections.actionMenuFragmentToRealtimeProcessFragment(
                CartoonFilterProcessor(),
                frontCamera = true
            ),"카툰 필터 적용하기",
        ),
        6
    )

    object SketchFilter: Menu(

        Direction.from(
            MenuFragmentDirections.actionMenuFragmentToRealtimeProcessFragment(
                processor = SketchFilterProcessor(),
                frontCamera = true
            ),"스케치 필터 적용하기",
        ),
        7
    )

}