package com.charlezz.opencvtutorial.features.feature

import com.charlezz.opencvtutorial.*

sealed class FeatureMenus {
    object Sobel : Menu(

        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(

                arrayOf(
                    BitmapImage ("원본",R.drawable.lenna),
                    SobelImage("dx=1, dy=0, ksize=3",-1, 1, 0, 3),
                    SobelImage("dx=0, dy=1, ksize=3",-1, 0, 1, 3)
                )
            ),title = "소벨 필터",
        ),
        order = 1
    )

    object Scharr : Menu(

        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(

                arrayOf(
                    BitmapImage ("원본",R.drawable.lenna),
                    ScharrImage("dx=1, dy=0, ksize=3",-1, 1, 0),
                    ScharrImage("dx=0, dy=1, ksize=3",-1, 0, 1)
                )
            ),title = "샤르 필터",
        ),
        order = 2
    )

    object Gradient : Menu(

        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(

                arrayOf(
                    BitmapImage ("원본",R.drawable.lenna),
                    GradientImage(),
                )
            ),title = "그라디언트 엣지 검출",
        ),
        order = 3
    )

    object Laplacian : Menu(

        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(

                arrayOf(
                    BitmapImage ("원본",R.drawable.lenna),
                    LaplacianImage("라플라시안 필터", -1, 3),
                )
            ),title = "라플라시안 필터",
        ),
        order = 4
    )

    object Canny : Menu(

        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(

                arrayOf(
                    BitmapImage ("원본",R.drawable.lenna),
                    CannyImage("캐니 엣지 검출", 50.0, 100.0),
                )
            ),title = "캐니 엣지 검출",
        ),
        order = 5
    )

    object HoughLinesTransform : Menu(

        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(

                arrayOf(
                    BitmapImage ("원본",R.drawable.building),
                    HoughLinesTransformImage()
                )
            ),title = "허프 선 변환",
        ),
        order = 6
    )


    object HoughLinesPTransform : Menu(

        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(

                arrayOf(
                    BitmapImage ("원본",R.drawable.building),
                    HoughLinesPTransformImage()
                )
            ),title = "확률적 허프 선 변환",
        ),
        order = 7
    )

    object HoughCirclesTransform : Menu(

        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(

                arrayOf(
                    BitmapImage ("원본",R.drawable.coin),
                    HoughCirclesTransformImage()
                )
            ),title = "허프 원 변환",
        ),
        order = 8
    )
}