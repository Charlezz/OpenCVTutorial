package com.charlezz.opencvtutorial

import com.charlezz.opencvtutorial.features.common.Processor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.multibindings.Multibinds

@InstallIn(ViewModelComponent::class)
@Module
abstract class ViewModelModule {
    @Multibinds
    abstract fun processorMap() : Map<Class<out Processor>, @JvmSuppressWildcards Processor>
}
