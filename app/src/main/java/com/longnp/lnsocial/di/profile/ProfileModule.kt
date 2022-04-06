package com.longnp.lnsocial.di.profile

import com.longnp.lnsocial.business.datasource.network.main.OpenApiMainService
import com.longnp.lnsocial.business.interactors.profile.GetVideoByType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideGetVideoByType(
        service: OpenApiMainService
    ): GetVideoByType {
        return GetVideoByType(
            service = service
        )
    }

}