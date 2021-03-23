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
    object Histogram: Menu(
        "히스토그램",
        MainMenuFragmentDirections.actionMainFragmentToHistogramFragment(),
        7
    )

}