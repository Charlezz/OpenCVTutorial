package com.charlezz.opencvtutorial

import com.xwray.groupie.GroupieAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CommonModule {

    @Provides
    fun provideGroupieAdapter(): GroupieAdapter {
        return GroupieAdapter()
    }
}