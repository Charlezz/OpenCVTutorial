package com.charlezz.opencvtutorial.features.geometry

import com.charlezz.opencvtutorial.Menu
import com.charlezz.opencvtutorial.MenuDirections
import com.charlezz.opencvtutorial.MenuFragmentDirections

sealed class GeometryMenus {
    object WarpAffine: Menu(
        "warpAffine() 예제",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToWarpAffineFragment()),
        0
    )
    object Resize: Menu(
        "리사이즈 예제",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToResizeFragment()),
        1
    )

    object Rotation: Menu(
        "이미지 회전 예제",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToRotationFragment()),
        2
    )

    object Flip: Menu(
        "이미지 대칭 예제",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToFlipFragment()),
        3
    )

    object Pyramid: Menu(
        "피라미드 예제",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToPyramidFragment()),
        4
    )
}