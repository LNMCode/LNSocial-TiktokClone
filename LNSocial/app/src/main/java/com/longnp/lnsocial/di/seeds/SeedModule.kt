package com.longnp.lnsocial.di.seeds

import com.longnp.lnsocial.business.datasource.network.main.OpenApiMainService
import com.longnp.lnsocial.business.interactors.video.SearchVideoSeeds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SeedModule {

    @Singleton
    @Provides
    fun provideSearchVideoSeeds(
        service: OpenApiMainService,
    ): SearchVideoSeeds {
        return SearchVideoSeeds(
            service = service
        )
    }

}