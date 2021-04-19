package com.charlezz.opencvtutorial.features.common

import org.opencv.core.Mat
import java.io.Serializable

interface Processor : Serializable {

    fun process(mat:Mat):Mat

}