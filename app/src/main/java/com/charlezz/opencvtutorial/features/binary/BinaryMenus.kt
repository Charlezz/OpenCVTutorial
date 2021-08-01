package com.charlezz.opencvtutorial.features.binary

import com.charlezz.opencvtutorial.*
import com.charlezz.opencvtutorial.features.feature.SobelImage
import org.opencv.imgcodecs.Imgcodecs

sealed class BinaryMenus {
    object Threshold : Menu(
        title = "Threshold",
        menuDirections = MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToSliderProcessFragment(
                ThresholdProcessor(),
                R.drawable.lenna,
                Imgcodecs.IMREAD_GRAYSCALE
            )
        ),
        order = 1
    )

}