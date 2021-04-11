package com.charlezz.opencvtutorial.features.basic

import com.charlezz.opencvtutorial.Menu
import com.charlezz.opencvtutorial.MenuDirections
import com.charlezz.opencvtutorial.MenuFragmentDirections
import com.charlezz.opencvtutorial.features.basic.arithmetic.ArithmeticMenus

sealed class BasicMenus {

    object ImageInfo : Menu(
        "이미지 속성 확인",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToImageInfoFragment()),
        0
    )

    object ImageChannels : Menu(
        "이미지 채널별 분리",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToImageChannelFragment()),
        1
    )

    object SimpleMask : Menu(
        "간단한 마스크 연산",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToSimpleMaskFragment()),
        2
    )

    object Drawing : Menu(
        "그리기",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToDrawingFragment()),
        3
    )

    object VideoCapture : Menu(
        "비디오 캡쳐",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToVideoCaptureFragment()),
        4

    )

    object Brightness : Menu(
        "밝기 조절",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToBrightnessFragment()),
        5
    )

    object Arithmetic : Menu(
        "산술 연산",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentSelf(
            ArithmeticMenus::class.nestedClasses
                .sortedBy { menuKclass -> (menuKclass.objectInstance as Menu).order }
                .map { menuKclass -> menuKclass.objectInstance as Menu }
                .toTypedArray()
        )),
        6
    )

    object Logical : Menu(
        "논리 연산",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToLogicalOperatorFragment()),
        6
    )

    object HSVColor : Menu(
        "HSV로 특정 색상 검출",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToHsvColorFragment()),
        7
    )

    object Histogram : Menu(
        "히스토그램",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToHistogramFragment()),
        8
    )

    object BackProject : Menu(
        "히스토그램 역투영",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToBackProjectFragment()),
        9
    )

    object ChromaKey : Menu(
        "크로마키 예제",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToChromaKeyFragment()),
        10
    )
}