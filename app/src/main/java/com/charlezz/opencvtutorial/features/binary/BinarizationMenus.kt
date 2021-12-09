package com.charlezz.opencvtutorial.features.binary

import com.charlezz.opencvtutorial.*
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc

sealed class BinarizationMenus {
    object Threshold : Menu(
        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToSliderProcessFragment(
                ThresholdProcessor(),
                R.drawable.lenna,
                Imgcodecs.IMREAD_GRAYSCALE
            ),
            "Threshold (이진화)",
        ),
        order = 1
    )

    object Otsu : Menu(
        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(

                arrayOf(
                    BitmapImage("레나", R.drawable.lenna),
                    OtsuImage("레나(Otsu)", R.drawable.lenna),
                    BitmapImage("세포", R.drawable.cells),
                    OtsuImage("세포(Otsu)", R.drawable.cells),
                    BitmapImage("밥알", R.drawable.rice),
                    OtsuImage("밥알(Otsu)", R.drawable.rice)
                )
            ),"Otsu (자동 이진화)",
        ),
        order = 2
    )

    object LocalThreshold : Menu(

        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(

                arrayOf(
                    BitmapImage("쌀알(원본)", R.drawable.rice),
                    OtsuImage("쌀알(Otsu)", R.drawable.rice, false),
                    LocalBinarizationImage("쌀알(지역이진화)", R.drawable.rice, 4, 4),

                    BitmapImage("스도쿠(원본)", R.drawable.sudoku),
                    OtsuImage("스도쿠(Otsu)", R.drawable.sudoku, false),
                    LocalBinarizationImage("스도쿠(지역이진화)", R.drawable.sudoku, 4, 4),
                )
            ),"지역 이진화",
        ),
        order = 3
    )

    object AdaptiveThreshold : Menu(
        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToSliderProcessFragment(

                AdaptiveThresholdProcessor(),
                R.drawable.sudoku,
                Imgcodecs.IMREAD_GRAYSCALE
            ), title = "적응형 이진화"
        ),
        order = 4
    )

    object Labeling : Menu(

        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(

                arrayOf(LabelingImage("레이블링"))
            ),   title = "레이블링",
        ),
        order = 5
    )

    object Erosion : Menu(
        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToSliderProcessFragment(
                MorphologyProcessor(true),
                R.drawable.rice,
                Imgcodecs.IMREAD_GRAYSCALE
            ),title = "침식"
        ),
        order = 6
    )

    object Dilation : Menu(
        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToSliderProcessFragment(
                MorphologyProcessor(false),
                R.drawable.rice,
                Imgcodecs.IMREAD_GRAYSCALE
            ),"팽창"
        ),
        order = 7
    )

    object MorphologyEx : Menu(
        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(

                arrayOf(
                    RiceWithOpeningImage(
                        "쌀알 + 열기(Opening) 연산",
                        R.drawable.rice,
                        Imgproc.MORPH_ERODE,
                        5
                    ),
                )
            ),"쌀알 + 열기(Opening) 연산",
        ),
        order = 8
    )

    object Contour : Menu(

        direction = Direction.from(

            MenuFragmentDirections.actionMenuFragmentToImageListFragment(
                arrayOf(
                    ContourImage(),
                )
            ),title = "윤곽선 검출(쌀알)",
        ),
        order = 9
    )

    object Contour2 : Menu(

        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(

                arrayOf(
                    ContourImage(_resId = R.drawable.milkdrop, useLocalBinarization = false),
                )
            ),title = "윤곽선 검출(물방울)",
        ),
        order = 10
    )

    object ArcLength : Menu(

        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(
                arrayOf(
                    ArcLengthImage(),
                )
            ),title = "윤곽선 길이 검출(arcLength)",
        ),
        order = 11
    )

    object ContourArea : Menu(

        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(

                arrayOf(
                    ContourAreaImage(),
                )
            ),title = "면적 구하기",
        ),
        order = 11
    )

    object BoundingRect : Menu(

        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(
                arrayOf(
                    BoundingRectImage(),
                )
            ),title = "윤곽선을 감싸는 사각형",
        ),
        order = 12
    )

    object MinEnclosingCircle : Menu(

        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(

                arrayOf(
                    MinEnclosingCircleImage(),
                )
            ),title = "윤곽선을 감싸는 원",
        ),
        order = 13
    )

    object MinAreaRectImage : Menu(

        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(

                arrayOf(
                    MinAreaRectImage(),
                )
            ),title = "윤곽선을 최소로 감싸는 회전된 사각형",
        ),
        order = 14
    )

    object MinEnclosingTriangle : Menu(

        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(
                arrayOf(
                    MinEnclosingTriangleImage(),
                )
            ),title = "윤곽선을 감싸는 삼각형"
        ),
        order = 15
    )

    object ApproxPolyDP : Menu(
        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToSliderProcessFragment(
                ApproxPolyDPProcessor(),
                R.drawable.polygon,
                Imgcodecs.IMREAD_COLOR
            ),"근사화"
        ),
        order = 16
    )

    object FitEllipse : Menu(

        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(
                arrayOf(
                    FitEllipseImage(),
                )
            ),title = "주어진 점에 적합한 타원 그리기"
        ),
        order = 17
    )

    object FitLine : Menu(

        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(
                arrayOf(
                    FitLineImage(),
                    FitLineImage(_resId = R.drawable.x),
                )
            ),title = "주어진 점에 적합한 직선을 반환"
        ),
        order = 18
    )

    object ConvexCheck : Menu(

        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(
                arrayOf(
                    CheckConvexImage(),
                )
            ),title = "컨벡스인지를 검사"
        ),
        order = 19
    )

    object ConvexHull : Menu(

        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(
                arrayOf(
                    ConvexHullImage(_resId = R.drawable.hand, useLocalBinarization = false),
                    ConvexHullImage(),
                )
            ),title = "주어진 점으로부터 컨벡스헐을 반환"
        ),
        order = 20
    )

    object ConvexityDefects : Menu(

        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(
                arrayOf(
                    ConvexityDefectsImage(_resId = R.drawable.hand, useLocalBinarization = false),
                )
            ),title = "주어진 점과 컨벡스 헐로부터 컨벡스 디펙트를 반환"
        ),
        order = 21
    )

}