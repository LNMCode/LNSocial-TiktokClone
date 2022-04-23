package com.longnp.lnsocial.di

import com.longnp.lnsocial.business.datasource.local.media.MediaLocalInteraction
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AddModule {

    @Singleton
    @Provides
    fun provideMediaLocalInteraction(): MediaLocalInteraction {
        return MediaLocalInteraction()
    }
}