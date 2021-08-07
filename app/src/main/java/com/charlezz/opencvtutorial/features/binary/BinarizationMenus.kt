package com.charlezz.opencvtutorial.features.binary

import com.charlezz.opencvtutorial.*
import com.charlezz.opencvtutorial.features.feature.HoughCirclesTransformImage
import org.opencv.imgcodecs.Imgcodecs

sealed class BinarizationMenus {
    object Threshold : Menu(
        title = "Threshold (이진화)",
        menuDirections = MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToSliderProcessFragment(
                ThresholdProcessor(),
                R.drawable.lenna,
                Imgcodecs.IMREAD_GRAYSCALE
            )
        ),
        order = 1
    )

    object Otsu : Menu(
        title = "Otsu (자동 이진화)",
        menuDirections = MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(
                arrayOf(
                    BitmapImage("레나", R.drawable.lenna),
                    OtsuImage("레나(Otsu)",R.drawable.lenna),
                    BitmapImage("세포", R.drawable.cells),
                    OtsuImage("세포(Otsu)",R.drawable.cells),
                    BitmapImage("밥알", R.drawable.rice),
                    OtsuImage("밥알(Otsu)",R.drawable.rice)
                )
            )
        ),
        order = 2
    )

    object LocalThreshold : Menu(
        title = "지역 이진화",
        menuDirections = MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(
                arrayOf(
                    BitmapImage("쌀알(원본)",R.drawable.rice),
                    OtsuImage("쌀알(Otsu)",R.drawable.rice, false),
                    LocalBinarizationImage("쌀알(지역이진화)", R.drawable.rice, 4,4),

                    BitmapImage("스도쿠(원본)",R.drawable.sudoku),
                    OtsuImage("스도쿠(Otsu)",R.drawable.sudoku, false),
                    LocalBinarizationImage("스도쿠(지역이진화)", R.drawable.sudoku, 4,4),
                )
            )
        ),
        order = 3
    )

    object AdaptiveThreshold : Menu(
        title = "적응형 이진화",
        menuDirections = MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToSliderProcessFragment(
                AdaptiveThresholdProcessor(),
                R.drawable.rice,
                Imgcodecs.IMREAD_GRAYSCALE
            )
        ),
        order = 4
    )

}