package com.charlezz.opencvtutorial.features.filtering

import com.charlezz.opencvtutorial.Menu
import com.charlezz.opencvtutorial.MenuDirections
import com.charlezz.opencvtutorial.MenuFragmentDirections
import com.charlezz.opencvtutorial.features.basic.arithmetic.ArithmeticMenus

sealed class FilteringMenus {

    object Filter2D : Menu(
        "기본적인 필터 적용하기",
        MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToSingleProcessFragment(
                Filter2DProcessor()
            )
        ),
        0
    )

    object Blur : Menu(
        "블러 적용하기",
        MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToSingleProcessFragment(
                BlurProcessor()
            )
        ),
        1
    )

    object GaussianBlur : Menu(
        "가우시안 블러 적용하기",
        MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToSingleProcessFragment(
                GaussianBlurProcessor()
            )
        ),
        2
    )

    object Sharpening : Menu(
        "언샤프 필터 적용하기",
        MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToSingleProcessFragment(
                SharpeningProcessor()
            )
        ),
        3
    )

}