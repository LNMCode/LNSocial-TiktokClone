package com.longnp.lnsocial.di.discovery

import com.longnp.lnsocial.business.interactors.discovery.SearchDiscovery
import com.longnp.lnsocial.presentation.main.discovery.DiscoveryDataRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiscoveryModule {

    @Singleton
    @Provides
    fun provideSearchDiscovery(
        dataRepoJson: DiscoveryDataRepo
    ): SearchDiscovery {
        return SearchDiscovery(
            dataRepoJson = dataRepoJson
        )
    }
}