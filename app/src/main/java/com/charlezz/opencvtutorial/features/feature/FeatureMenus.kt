package com.charlezz.opencvtutorial.features.feature

import com.charlezz.opencvtutorial.*

sealed class FeatureMenus {
    object Sobel : Menu(
        title = "소벨 필터",
        menuDirections = MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(
                arrayOf(
                    BitmapImage ("원본",R.drawable.lenna),
                    SobelImage("dx=1, dy=0, ksize=3",-1, 1, 0, 3),
                    SobelImage("dx=0, dy=1, ksize=3",-1, 0, 1, 3)
                )
            )
        ),
        order = 1
    )

    object Scharr : Menu(
        title = "샤르 필터",
        menuDirections = MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(
                arrayOf(
                    BitmapImage ("원본",R.drawable.lenna),
                    ScharrImage("dx=1, dy=0, ksize=3",-1, 1, 0),
                    ScharrImage("dx=0, dy=1, ksize=3",-1, 0, 1)
                )
            )
        ),
        order = 2
    )

    object Gradient : Menu(
        title = "그라디언트 엣지 검출",
        menuDirections = MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(
                arrayOf(
                    BitmapImage ("원본",R.drawable.lenna),
                    GradientImage(),
                )
            )
        ),
        order = 3
    )
}