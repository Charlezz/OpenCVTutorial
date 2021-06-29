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

    object Laplacian : Menu(
        title = "라플라시안 필터",
        menuDirections = MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(
                arrayOf(
                    BitmapImage ("원본",R.drawable.lenna),
                    LaplacianImage("라플라시안 필터", -1, 3),
                )
            )
        ),
        order = 4
    )

    object Canny : Menu(
        title = "캐니 엣지 검출",
        menuDirections = MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(
                arrayOf(
                    BitmapImage ("원본",R.drawable.lenna),
                    CannyImage("캐니 엣지 검출", 50.0, 100.0),
                )
            )
        ),
        order = 5
    )

    object HoughLinesTransform : Menu(
        title = "허프 선 변환",
        menuDirections = MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(
                arrayOf(
                    BitmapImage ("원본",R.drawable.building),
                    HoughLinesTransformImage()
                )
            )
        ),
        order = 6
    )


    object HoughLinesPTransform : Menu(
        title = "확률적 허프 선 변환",
        menuDirections = MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(
                arrayOf(
                    BitmapImage ("원본",R.drawable.building),
                    HoughLinesPTransformImage()
                )
            )
        ),
        order = 7
    )

    object HoughCirclesTransform : Menu(
        title = "허프 원 변환",
        menuDirections = MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(
                arrayOf(
                    BitmapImage ("원본",R.drawable.coin),
                    HoughCirclesTransformImage()
                )
            )
        ),
        order = 8
    )
}