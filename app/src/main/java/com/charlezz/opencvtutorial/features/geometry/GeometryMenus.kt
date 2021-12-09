package com.charlezz.opencvtutorial.features.geometry

import com.charlezz.opencvtutorial.*

sealed class GeometryMenus {
    object WarpAffine : Menu(

        Direction.from(MenuFragmentDirections.actionMenuFragmentToWarpAffineFragment(), "warpAffine() 예제"),
        0
    )

    object Resize : Menu(

        Direction.from(MenuFragmentDirections.actionMenuFragmentToResizeFragment(),"리사이즈 예제",),
        1
    )

    object Rotation : Menu(

        Direction.from(MenuFragmentDirections.actionMenuFragmentToRotationFragment(),"이미지 회전 예제",),
        2
    )

    object Flip : Menu(

        Direction.from(MenuFragmentDirections.actionMenuFragmentToFlipFragment(),"이미지 대칭 예제",),
        3
    )

    object Pyramid : Menu(

        Direction.from(MenuFragmentDirections.actionMenuFragmentToPyramidFragment(),"피라미드 예제",),
        4
    )

    object AffineTransform : Menu(

        Direction.from(MenuFragmentDirections.actionMenuFragmentToAffineTransformFragment(),"Affine 변환 행렬 구하기",),
        5
    )

    object PerspectiveTransform : Menu(

        Direction.from(MenuFragmentDirections.actionMenuFragmentToPerspectiveTransformFragment(),"Perspective 변환 행렬 구하기",),
        6
    )

    object Remap : Menu(

        Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(

                arrayOf(
                    BitmapImage("",R.drawable.runa),
                    RemapWave()
                )
            ),title = "리매핑",
        ),
        7
    )

    object Scanner : Menu(
        Direction.from(MenuFragmentDirections.actionMenuFragmentToScannerFragment(),"문서 스캐너"),
        8
    )
}