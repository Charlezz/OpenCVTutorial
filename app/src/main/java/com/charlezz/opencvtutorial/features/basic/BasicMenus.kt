package com.charlezz.opencvtutorial.features.basic

import com.charlezz.opencvtutorial.Direction
import com.charlezz.opencvtutorial.Menu
import com.charlezz.opencvtutorial.MenuFragmentDirections
import com.charlezz.opencvtutorial.features.basic.arithmetic.ArithmeticMenus

sealed class BasicMenus {

    object ImageInfo : Menu(

        Direction.from( MenuFragmentDirections.actionMenuFragmentToImageInfoFragment(),"이미지 속성 확인",),
        0
    )

    object ImageChannels : Menu(

        Direction.from(

            MenuFragmentDirections.actionMenuFragmentToImageChannelFragment(),"이미지 채널별 분리",
        ),
        1
    )

    object SimpleMask : Menu(

        Direction.from(

            MenuFragmentDirections.actionMenuFragmentToSimpleMaskFragment(),"간단한 마스크 연산",
        ),
        2
    )

    object Drawing : Menu(

        Direction.from( MenuFragmentDirections.actionMenuFragmentToDrawingFragment(),"그리기",),
        3
    )

    object VideoCapture : Menu(

        Direction.from( MenuFragmentDirections.actionMenuFragmentToVideoCaptureFragment(),"비디오 캡쳐",),
        4

    )

    object Brightness : Menu(

        Direction.from(MenuFragmentDirections.actionMenuFragmentToBrightnessFragment(), "밝기 조절"),
        5
    )

    object Arithmetic : Menu(

        Direction.from(
            navDirections = MenuFragmentDirections.actionMenuFragmentSelf(
                ArithmeticMenus::class.nestedClasses
                    .sortedBy { menuKclass -> (menuKclass.objectInstance as Menu).order }
                    .map { menuKclass -> menuKclass.objectInstance as Menu }
                    .toTypedArray()
            ),"산술 연산",),
        6
    )

    object Logical : Menu(
        Direction.from(
            MenuFragmentDirections.actionMenuFragmentToLogicalOperatorFragment(),
            "논리 연산"
        ),
        6
    )

    object HSVColor : Menu(

        Direction.from(
            MenuFragmentDirections.actionMenuFragmentToHsvColorFragment(),
            "HSV로 특정 색상 검출",
        ),
        7
    )

    object Histogram : Menu(

        Direction.from(MenuFragmentDirections.actionMenuFragmentToHistogramFragment(), "히스토그램"),
        8
    )

    object BackProject : Menu(

        Direction.from(
            MenuFragmentDirections.actionMenuFragmentToBackProjectFragment(),
            "히스토그램 역투영",
        ),
        9
    )

    object ChromaKey : Menu(

        Direction.from(MenuFragmentDirections.actionMenuFragmentToChromaKeyFragment(), "크로마키 예제"),
        10
    )
}