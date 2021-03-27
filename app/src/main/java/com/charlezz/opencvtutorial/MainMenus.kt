package com.charlezz.opencvtutorial

import android.graphics.ImageDecoder
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.navigation.NavDirections

sealed class MainMenus{
    object ImageInfo : Menu(
        "이미지 속성 확인",
        MainMenuFragmentDirections.actionMainFragmentToImageInfoFragment(),
        0
    )
    object ImageChannels:Menu(
        "이미지 채널별 분리",
        MainMenuFragmentDirections.actionMainFragmentToImageChannelFragment(),
        1
    )
    object SimpleMask:Menu(
        "간단한 마스크 연산",
        MainMenuFragmentDirections.actionMainFragmentToSimpleMaskFragment(),
        2
    )
    object Drawing: Menu(
        "그리기",
        MainMenuFragmentDirections.actionMainFragmentToDrawingFragment(),
        3
    )
    object VideoCapture: Menu(
        "비디오 캡쳐",
        MainMenuFragmentDirections.actionMainFragmentToVideoCaptureFragment(),
        4

    )
    object Brightness: Menu(
        "밝기 조절",
        MainMenuFragmentDirections.actionMainFragmentToBrightnessFragment(),
        5
    )
    object Arithmetic: Menu(
        "산술 연산",
        MainMenuFragmentDirections.actionMainFragmentToArithmeticFragment(),
        6
    )
    object Logical: Menu(
        "논리 연산",
        MainMenuFragmentDirections.actionMainFragmentToLogicalOperatorFragment(),
        6
    )
    object HSVColor: Menu(
        "HSV로 특정 색상 검출",
        MainMenuFragmentDirections.actionMainFragmentToHsvColorFragment(),
        7
    )
    object Histogram: Menu(
        "히스토그램",
        MainMenuFragmentDirections.actionMainFragmentToHistogramFragment(),
        8
    )
    object BackProject: Menu(
        "히스토그램 역투영",
        MainMenuFragmentDirections.actionMainFragmentToBackProjectFragment(),
        9
    )

    object ChromaKey: Menu(
        "크로마키 예제",
        MainMenuFragmentDirections.actionMainFragmentToChromaKeyFragment(),
        10
    )



}