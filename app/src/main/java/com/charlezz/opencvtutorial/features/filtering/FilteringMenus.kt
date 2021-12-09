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
                Filter2DProcessor(),
                R.drawable.lenna,
                Imgcodecs.IMREAD_GRAYSCALE
            ),"기본적인 필터 적용하기",
        ),
        0
    )

    object Blur : Menu(
        Direction.from(

            MenuFragmentDirections.actionMenuFragmentToSingleProcessFragment(
                BlurProcessor(),
                R.drawable.lenna,
                Imgcodecs.IMREAD_GRAYSCALE
            ),"블러 적용하기",
        ),
        1
    )

    object GaussianBlur : Menu(

        Direction.from(
            MenuFragmentDirections.actionMenuFragmentToSingleProcessFragment(
                GaussianBlurProcessor(),
                R.drawable.lenna,
                Imgcodecs.IMREAD_GRAYSCALE
            ),"가우시안 블러 적용하기",
        ),
        2
    )

    object Sharpening : Menu(

        Direction.from(
            MenuFragmentDirections.actionMenuFragmentToSingleProcessFragment(
                SharpeningProcessor(),
                R.drawable.lenna,
                Imgcodecs.IMREAD_GRAYSCALE
            ),"언샤프 필터 적용하기",
        ),
        3
    )

    object MedianFilter: Menu(

        Direction.from(
            MenuFragmentDirections.actionMenuFragmentToSingleProcessFragment(
                MedianFilterProcessor(),
                R.drawable.lenna_salt_pepper,
                Imgcodecs.IMREAD_GRAYSCALE
            ),"미디언 필터 적용하기",
        ),
        4
    )

    object BilateralFilter: Menu(

        Direction.from(
            MenuFragmentDirections.actionMenuFragmentToSingleProcessFragment(
                MedianFilterProcessor(),
                R.drawable.lenna,
                Imgcodecs.IMREAD_COLOR
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
                SketchFilterProcessor(),
                frontCamera = true
            ),"스케치 필터 적용하기",
        ),
        7
    )

}