package com.charlezz.opencvtutorial.features.geometry

import com.charlezz.opencvtutorial.*

sealed class GeometryMenus {
    object WarpAffine : Menu(
        "warpAffine() 예제",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToWarpAffineFragment()),
        0
    )

    object Resize : Menu(
        "리사이즈 예제",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToResizeFragment()),
        1
    )

    object Rotation : Menu(
        "이미지 회전 예제",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToRotationFragment()),
        2
    )

    object Flip : Menu(
        "이미지 대칭 예제",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToFlipFragment()),
        3
    )

    object Pyramid : Menu(
        "피라미드 예제",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToPyramidFragment()),
        4
    )

    object AffineTransform : Menu(
        "Affine 변환 행렬 구하기",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToAffineTransformFragment()),
        5
    )

    object PerspectiveTransform : Menu(
        "Perspective 변환 행렬 구하기",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToPerspectiveTransformFragment()),
        6
    )

    object Remap : Menu(
        "리매핑",
        MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(
                arrayOf(
                    BitmapImage(R.drawable.runa),
                    RemapWave()
                )
            )
        ),
        7
    )

    object Scanner : Menu(
        "문서 스캐너",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToScannerFragment()),
        8
    )
}