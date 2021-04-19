package com.charlezz.opencvtutorial.features.filtering

import com.charlezz.opencvtutorial.Menu
import com.charlezz.opencvtutorial.MenuDirections
import com.charlezz.opencvtutorial.MenuFragmentDirections
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.features.basic.arithmetic.ArithmeticMenus
import org.opencv.imgcodecs.Imgcodecs

sealed class FilteringMenus {

    object Filter2D : Menu(
        "기본적인 필터 적용하기",
        MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToSingleProcessFragment(
                Filter2DProcessor(),
                R.drawable.lenna,
                Imgcodecs.IMREAD_GRAYSCALE
            )
        ),
        0
    )

    object Blur : Menu(
        "블러 적용하기",
        MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToSingleProcessFragment(
                BlurProcessor(),
                R.drawable.lenna,
                Imgcodecs.IMREAD_GRAYSCALE
            )
        ),
        1
    )

    object GaussianBlur : Menu(
        "가우시안 블러 적용하기",
        MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToSingleProcessFragment(
                GaussianBlurProcessor(),
                R.drawable.lenna,
                Imgcodecs.IMREAD_GRAYSCALE
            )
        ),
        2
    )

    object Sharpening : Menu(
        "언샤프 필터 적용하기",
        MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToSingleProcessFragment(
                SharpeningProcessor(),
                R.drawable.lenna,
                Imgcodecs.IMREAD_GRAYSCALE
            )
        ),
        3
    )

    object MedianFilter: Menu(
        "미디언 필터 적용하기",
        MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToSingleProcessFragment(
                MedianFilterProcessor(),
                R.drawable.lenna_salt_pepper,
                Imgcodecs.IMREAD_GRAYSCALE
            )
        ),
        4
    )

    object BilateralFilter: Menu(
        "양방향 필터 적용하기",
        MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToSingleProcessFragment(
                MedianFilterProcessor(),
                R.drawable.lenna,
                Imgcodecs.IMREAD_COLOR
            )
        ),
        5
    )

    object CartoonFilter: Menu(
        "카툰 필터 적용하기",
        MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToRealtimeProcessFragment(
                CartoonFilterProcessor(),
                frontCamera = true
            )
        ),
        6
    )

    object SketchFilter: Menu(
        "스케치 필터 적용하기",
        MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToRealtimeProcessFragment(
                SketchFilterProcessor(),
                frontCamera = true
            )
        ),
        7
    )

}