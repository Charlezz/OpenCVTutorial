package com.charlezz.opencvtutorial.navigation

import androidx.compose.runtime.Composable
import com.charlezz.opencvtutorial.presentation.screen.basic.*
import com.charlezz.opencvtutorial.presentation.screen.binary.*
import com.charlezz.opencvtutorial.presentation.screen.extraction.GrabCutContent
import com.charlezz.opencvtutorial.presentation.screen.extraction.MatchShapesContent
import com.charlezz.opencvtutorial.presentation.screen.feature.*
import com.charlezz.opencvtutorial.presentation.screen.filter.*
import com.charlezz.opencvtutorial.presentation.screen.geometry.*

/**
 * @author soohwan.ok
 */
@Suppress("ClassName")
sealed class Screen(
    val title: String,
    val route: Route,
    val content: @Composable (() -> Unit)? = null
) {
    object Home : Screen(
        title = "Home",
        route = Route("home_screen"),
    )

    /**
     * 1. Basic
     */

    object Menu1Basic : Screen(
        title = "Basic",
        route = Route("menu1_basic_screen"),
    ) {
        val screens = arrayOf(
            ImageInfo,
            ImageChannels,
            SimpleMask,
            Drawing,
            VideoCapture,
            Brightness,
            Add,
            AddWeighted,
            Subtract,
            AbsDiff,
            LogicalOperation,
            HSVColor,
            Histogram,
            BackProject,
            Chromakey
        )

        object ImageInfo : Screen(
            title = "Image Information",
            route = Route("menu1_basic_image_info_screen"),
            content = { ImageInfoContent() },
        )

        object ImageChannels : Screen(
            title = "Image Channels",
            route = Route("menu1_basic_image_channels_screen"),
            content = { ImageChannelsContent() },
        )

        object SimpleMask : Screen(
            title = "Simple Mask",
            route = Route("menu1_basic_simple_mask_screen"),
            content = { SimpleMaskContent() },
        )

        object Drawing : Screen(
            title = "Drawing",
            route = Route("menu1_basic_drawing_screen"),
            content = { DrawingContent() },
        )

        object VideoCapture : Screen(
            title = "VideoCapture",
            route = Route("menu1_basic_video_capture_screen"),
            content = { VideoCaptureContent() },
        )

        object Brightness : Screen(
            title = "Brightness",
            route = Route("menu1_basic_brightness_screen"),
            content = { BrightnessContent() },
        )

        object Add : Screen(
            title = "Add",
            route = Route("menu1_basic_add_screen"),
            content = { AddContent() },
        )

        object AddWeighted : Screen(
            title = "Add Weighted",
            route = Route("menu1_basic_add_weighted_screen"),
            content = { AddWeightedContent() },
        )

        object Subtract : Screen(
            title = "Subtract",
            route = Route("menu1_basic_subtract_screen"),
            content = { SubtractContent() },
        )

        object AbsDiff : Screen(
            title = "AbsDiff",
            route = Route("menu1_basic_abs_diff_screen"),
            content = { AbsDiffContent() },
        )

        object LogicalOperation : Screen(
            title = "Logical Operation",
            route = Route("menu1_basic_logical_operation_screen"),
            content = { LogicalOperationContent() },
        )

        object HSVColor : Screen(
            title = "HSVColor",
            route = Route("menu1_basic_hsv_color_screen"),
            content = { HSVColorContent() },
        )

        object Histogram : Screen(
            title = "Histogram",
            route = Route("menu1_basic_histogram_screen"),
            content = { HistogramContent() },
        )

        object BackProject : Screen(
            title = "BackProject",
            route = Route("menu1_basic_back_project_screen"),
            content = { BackProjectContent() },
        )

        object Chromakey : Screen(
            title = "Chromakey",
            route = Route("menu1_basic_chromakey_screen"),
            content = { ChromakeyContent() },
        )
    }

    /**
     * 2. Filter
     */
    object Menu2Filter : Screen(
        title = "Filter",
        route = Route("menu2_filter_screen"),
        content = {},
    ) {
        val screens = arrayOf(
            Filter2D,
            Blur,
            GaussianBlur,
            Unsharp,
            Median,
            Bilateral,
            Cartoon,
            Sketch
        )

        object Filter2D : Screen(
            title = "Filter2D",
            route = Route("menu2_filter_filter2d"),
            content = {
                Filter2DContent()
            },
        )

        object Blur : Screen(
            title = "Blur",
            route = Route("menu2_filter_blur"),
            content = {
                BlurContent()
            },
        )

        object GaussianBlur : Screen(
            title = "GaussianBlur",
            route = Route("menu2_filter_gaussian_blur"),
            content = {
                GaussianBlurContent()
            },
        )

        object Unsharp : Screen(
            title = "Unsharp Filter",
            route = Route("menu2_filter_unsharp"),
            content = {
                UnsharpFilterContent()
            },
        )

        object Median : Screen(
            title = "Median Filter",
            route = Route("menu2_filter_median"),
            content = {
                MedianFilterContent()
            },
        )

        object Bilateral : Screen(
            title = "Bilateral Filter",
            route = Route("menu2_filter_bilateral"),
            content = {
                BilateralFilterContent()
            },
        )

        object Cartoon : Screen(
            title = "Cartoon Filter",
            route = Route("menu2_filter_cartoon"),
            content = { CartoonContent() },
        )

        object Sketch : Screen(
            title = "Sketch Filter",
            route = Route("menu2_filter_sketch"),
            content = { SketchContent() },
        )
    }

    /**
     * 3. Geometry
     */
    object Menu3GeometryTransformation : Screen(
        title = "Geometry Transformation",
        route = Route("menu3_geometry_transformation"),
    ) {

        val screens = arrayOf(
            WarpAffine,
            Resize,
            Rotation,
            Flip,
            Pyramid,
            AffineTransform,
            PerspectiveTransform,
            Remapping,
            DocumentScanner
        )

        object WarpAffine : Screen(
            title = "Warp Affine",
            route = Route("menu3_geometry_transformation_warpaffine"),
            content = {
                WarpAffineContent()
            },
        )

        object Resize : Screen(
            title = "Resize",
            route = Route("menu3_geometry_transformation_resize"),
            content = {
                ResizeContent()
            },
        )

        object Rotation : Screen(
            title = "Rotation",
            route = Route("menu3_geometry_transformation_rotation"),
            content = {
                RotationContent()
            },
        )

        object Flip : Screen(
            title = "Flip",
            route = Route("menu3_geometry_transformation_flip"),
            content = {
                FlipContent()
            },
        )

        object Pyramid : Screen(
            title = "Pyramid",
            route = Route("menu3_geometry_transformation_pyramid"),
            content = {
                PyramidContent()
            },
        )

        object AffineTransform : Screen(
            title = "AffineTransform",
            route = Route("menu3_geometry_transformation_affine_transform"),
            content = {
                AffineTransformContent()
            },
        )

        object PerspectiveTransform : Screen(
            title = "PerspectiveTransform",
            route = Route("menu3_geometry_transformation_perspective_transform"),
            content = {
                PerspectiveTransformContent()
            },
        )

        object Remapping : Screen(
            title = "Remapping",
            route = Route("menu3_geometry_transformation_remapping"),
            content = {
                RemappingContent()
            },
        )

        object DocumentScanner : Screen(
            title = "Document Scanner",
            route = Route("menu3_geometry_transformation_document_scanner"),
            content = {
                DocumentScannerContent()
            },
        )
    }

    /**
     * 4. Feature extraction
     */
    object Menu4FeatureExtraction : Screen(
        title = "Feature Extraction",
        route = Route("menu4_feature_extraction"),
    ) {

        val screens = arrayOf(
            SobelOperator,
            ScharrOperator,
            GradientEdgeDetection,
            LaplacianOperator,
            HoughLine,
            HoughLineP,
            HoughCircle,
        )

        object SobelOperator : Screen(
            title = "Sobel operator",
            route = Route("menu4_feature_extraction_sobel"),
            content = {
                SobelContent()
            }
        )

        object ScharrOperator : Screen(
            title = "Scharr operator",
            route = Route("menu4_feature_extraction_scharr"),
            content = {
                ScharrContent()
            }
        )

        object GradientEdgeDetection : Screen(
            title = "Gradient Edge Detection",
            route = Route("menu4_feature_extraction_gradient_edge_detection"),
            content = {
                GradientEdgeContent()
            }
        )

        object LaplacianOperator : Screen(
            title = "Laplacian operator",
            route = Route("menu4_feature_extraction_laplacian"),
            content = {
                LaplacianContent()
            }
        )

        object HoughLine : Screen(
            title = "Hough Line Transform",
            route = Route("menu4_feature_extraction_hough_line_transform"),
            content = {
                HoughLineContent()
            }
        )

        object HoughLineP : Screen(
            title = "Hough Line(Probability) Transform",
            route = Route("menu4_feature_extraction_hough_line_p_transform"),
            content = {
                HoughLinesPContent()
            }
        )

        object HoughCircle : Screen(
            title = "Hough Circle Transform",
            route = Route("menu4_feature_extraction_hough_circle_transform"),
            content = {
                HoughCircleContent()
            }
        )

    }

    object Menu5Binarization : Screen(
        title = "Binarization",
        route = Route("menu5_binarization"),
    ) {
        val screens = arrayOf(
            Threshold,
            LocalBinarization,
            AdaptiveThreshold,
            Labeling,
            Erosion,
            Dilation,
            Opening,
            Contour,
            ContourPerimeter,
            ContourArea,
            BoundingRectangle,
            BoundingRectangleMin,
            MinimumEnclosingCircle,
            MinimumEnclosingTriangle,
            ContourApproximation,
            FittingEllipse,
            FittingLine,
            CheckingConvexity,
            ConvexHull,
            ConvexityDefects
        )

        object Threshold : Screen(
            title = "Threshold",
            route = Route("menu5_binarization_threshold"),
            content = {
                ThresholdContent()
            },
        )

        object LocalBinarization : Screen(
            title = "Local Binarization",
            route = Route("menu5_binarization_local_binarization"),
            content = {
                LocalBinarizationContent()
            },
        )

        object AdaptiveThreshold : Screen(
            title = "Adaptive Threshold",
            route = Route("menu5_binarization_adaptive_threshold"),
            content = {
                AdaptiveThresholdContent()
            },
        )

        object Labeling : Screen(
            title = "Labeling",
            route = Route("menu5_binarization_labeling"),
            content = { LabelingContent() },
        )

        object Erosion : Screen(
            title = "Erosion",
            route = Route("menu5_binarization_erosion"),
            content = { ErosionContent() },
        )

        object Dilation : Screen(
            title = "Dilation",
            route = Route("menu5_binarization_dilate"),
            content = { DilationContent() },
        )

        object Opening : Screen(
            title = "Opening operation",
            route = Route("menu5_binarization_opening"),
            content = { OpeningContent() },
        )

        object Contour : Screen(
            title = "Contour",
            route = Route("menu5_binarization_contour"),
            content = { ContourContent() },
        )

        object ContourPerimeter : Screen(
            title = "Contour Perimeter",
            route = Route("menu5_binarization_contour_perimeter"),
            content = { ContourPerimeterContent() },
        )

        object ContourArea : Screen(
            title = "Contour Area",
            route = Route("menu5_binarization_contour_area"),
            content = { ContourAreaContent() },
        )

        object BoundingRectangle : Screen(
            title = "Bounding Rectangle",
            route = Route("menu5_binarization_bounding_rectangle"),
            content = { BoundingRectangleContent() },
        )

        object BoundingRectangleMin : Screen(
            title = "Bounding Rectangle with minimum area",
            route = Route("menu5_binarization_rotated_rectangle"),
            content = { BoundingRectangleMinContent() },
        )

        object MinimumEnclosingCircle : Screen(
            title = "Minimum Enclosing Circle",
            route = Route("menu5_binarization_minimum_enclosing_circle"),
            content = { MinimumEnclosingCircleContent() },
        )

        object MinimumEnclosingTriangle : Screen(
            title = "Minimum Enclosing Triangle",
            route = Route("menu5_binarization_minimum_enclosing_triangle"),
            content = { MinimumEnclosingTriangleContent() },
        )

        object ContourApproximation : Screen(
            title = "Contour Approximation",
            route = Route("menu5_binarization_contour_approximation"),
            content = { ContourApproximationContent() }
        )

        object FittingEllipse : Screen(
            title = "Fitting an Ellipse",
            route = Route("menu5_binarization_fitting_an_ellipse"),
            content = { FittingEllipseContent() }
        )

        object FittingLine : Screen(
            title = "Fitting a Line",
            route = Route("menu5_binarization_fitting_an_line"),
            content = { FittingLineContent() }
        )

        object CheckingConvexity : Screen(
            title = "Checking Convexity",
            route = Route("menu5_binarization_checking_convexity"),
            content = { CheckingConvexityContent() }
        )

        object ConvexHull : Screen(
            title = "Convex Hull",
            route = Route("menu5_binarization_convex_hull"),
            content = { ConvexHullContent() }
        )

        object ConvexityDefects : Screen(
            title = "Convexity Defects",
            route = Route("menu5_binarization_convexity_defects"),
            content = { ConvexityDefectsContent() }
        )
    }

    object Menu6Extraction : Screen(
        title = "Extraction",
        route = Route("menu6_extraction"),
    ) {
        val screens = arrayOf(GrabCut, MatchShapes)

        object GrabCut : Screen(
            title = "GrabCut",
            route = Route("menu6_extraction_grabcut"),
            content = { GrabCutContent() }
        )

        object MatchShapes : Screen(
            title = "Match Shapes",
            route = Route("menu6_extraction_match_shapes"),
            content = { MatchShapesContent() }
        )
    }
}