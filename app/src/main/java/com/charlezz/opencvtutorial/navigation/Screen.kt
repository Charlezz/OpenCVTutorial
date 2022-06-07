package com.charlezz.opencvtutorial.navigation

import androidx.compose.runtime.Composable
import com.charlezz.notepad.domain.model.Route
import com.charlezz.opencvtutorial.presentation.screen.basic.*

/**
 * @author soohwan.ok
 */
@Suppress("ClassName")
sealed class Screen(
    val title: String,
    val route: Route,
    val content: @Composable () -> Unit
) {
    object Home : Screen(
        title = "Home",
        route = Route("home_screen"),
        content = {}
    )

    /**
     * 1. Basic
     */

    object Menu1Basic : Screen(
        title = "Basic",
        route = Route("menu1_basic_screen"),
        content = {},
    )

    object Menu1Basic_ImageInfo : Screen(
        title = "Image Information",
        route = Route("menu1_basic_image_info_screen"),
        content = { ImageInfoContent() },
    )

    object Menu1Basic_ImageChannels : Screen(
        title = "Image Channels",
        route = Route("menu1_basic_image_channels_screen"),
        content = { ImageChannelsContent() },
    )

    object Menu1Basic_SimpleMask : Screen(
        title = "Simple Mask",
        route = Route("menu1_basic_simple_mask_screen"),
        content = { SimpleMaskContent() },
    )

    object Menu1Basic_Drawing : Screen(
        title = "Drawing",
        route = Route("menu1_basic_drawing_screen"),
        content = { DrawingContent() },
    )

    object Menu1Basic_VideoCapture : Screen(
        title = "VideoCapture",
        route = Route("menu1_basic_video_capture_screen"),
        content = { VideoCaptureContent() },
    )

    object Menu1Basic_Brightness : Screen(
        title = "Brightness",
        route = Route("menu1_basic_brightness_screen"),
        content = { BrightnessContent() },
    )

    object Menu1Basic_Add : Screen(
        title = "Add",
        route = Route("menu1_basic_add_screen"),
        content = { AddContent() },
    )

    object Menu1Basic_AddWeighted : Screen(
        title = "Add Weighted",
        route = Route("menu1_basic_add_weighted_screen"),
        content = { AddWeightedContent() },
    )

    object Menu1Basic_Subtract : Screen(
        title = "Subtract",
        route = Route("menu1_basic_subtract_screen"),
        content = { SubtractContent() },
    )

    object Menu1Basic_AbsDiff : Screen(
        title = "AbsDiff",
        route = Route("menu1_basic_abs_diff_screen"),
        content = { AbsDiffContent() },
    )

    object Menu1Basic_LogicalOperation : Screen(
        title = "Logical Operation",
        route = Route("menu1_basic_logical_operation_screen"),
        content = { LogicalOperationContent() },
    )

    object Menu1Basic_HSVColor : Screen(
        title = "HSVColor",
        route = Route("menu1_basic_hsv_color_screen"),
        content = { HSVColorContent() },
    )
    object Menu1Basic_Histogram : Screen(
        title = "Histogram",
        route = Route("menu1_basic_histogram_screen"),
        content = { HistogramContent() },
    )
    object Menu1Basic_BackProject : Screen(
        title = "BackProject",
        route = Route("menu1_basic_back_project_screen"),
        content = { BackProjectContent() },
    )
    object Menu1Basic_Chromakey : Screen(
        title = "Chromakey",
        route = Route("menu1_basic_chromakey_screen"),
        content = { ChromakeyContent() },
    )


    /**
     * 2. Filter
     */
    object Menu2Filter : Screen(
        title = "Filter",
        route = Route("menu2_filter_screen"),
        content = {},
    )
}