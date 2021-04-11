package com.charlezz.opencvtutorial

import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import dagger.hilt.android.HiltAndroidApp
import org.opencv.android.OpenCVLoader
import timber.log.Timber

@HiltAndroidApp
class App : Application(){

    init {
        val isIntialized = OpenCVLoader.initDebug()
        Timber.e("isIntialized = $isIntialized")
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}