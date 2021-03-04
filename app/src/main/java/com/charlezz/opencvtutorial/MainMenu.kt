package com.charlezz.opencvtutorial

import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.navigation.NavDirections

enum class MainMenu(
    val title: String,
    val direction: NavDirections
) {

    IMAGE_INFO(
        "이미지 속성 확인",
        MainMenuFragmentDirections.actionMainFragmentToImageInfoFragment()
    ),
    IMAGE_CHANNEL(
        "이미지 채널별 분리",
        MainMenuFragmentDirections.actionMainFragmentToImageChannelFragment()
    ),
    SIMPLE_MASK(
        "간단한 마스크 연산",
        MainMenuFragmentDirections.actionMainFragmentToSimpleMaskFragment()
    ),
    DRWAING(
        "그리기",
        MainMenuFragmentDirections.actionMainFragmentToDrawingFragment()
    ),
    VIDEO_CAPUTRE(
        "비디오 캡쳐",
        MainMenuFragmentDirections.actionMainFragmentToVideoCaptureFragment()
    )


}