package com.longnp.lnsocial.di

import com.longnp.lnsocial.business.datasource.local.media.MediaLocalInteraction
import com.longnp.lnsocial.business.datasource.local.space.DefaultLocalSpaceRepo
import com.longnp.lnsocial.business.datasource.network.main.OpenApiMainService
import com.longnp.lnsocial.business.interactors.post.PostVideo
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

    @Singleton
    @Provides
    fun providePostVideoInteraction(
        service: OpenApiMainService,
        defaultLocalSpaceRepo: DefaultLocalSpaceRepo,
    ): PostVideo {
        return PostVideo(
            service = service,
            localSpaceRepo = defaultLocalSpaceRepo,
        )
    }

}