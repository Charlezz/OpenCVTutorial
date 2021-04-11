package com.charlezz.opencvtutorial

import android.app.Application
import android.content.Context
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupieAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SingletonModule {

    @Provides
    fun provideGroupieAdapter(): GroupieAdapter {
        return GroupieAdapter()
    }
    
    @Provides
    fun provideLinearLayoutManager(app: Application):LinearLayoutManager{
        return LinearLayoutManager(app)
    }
}