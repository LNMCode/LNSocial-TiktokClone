package com.longnp.lnsocial.di.seeds

import android.app.Application
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.longnp.lnsocial.business.datasource.network.main.OpenApiMainService
import com.longnp.lnsocial.business.interactors.video.SearchVideoSeeds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun provideLeastRecentlyUsedCacheEvictor(): LeastRecentlyUsedCacheEvictor{
        return LeastRecentlyUsedCacheEvictor(90 * 1024 * 1024)
    }

    @Singleton
    @Provides
    fun provideExoDatabaseProvider(application: Application): DatabaseProvider {
        return StandaloneDatabaseProvider(application.applicationContext)
    }

    @Singleton
    @Provides
    fun provideSimpleCache(
        application: Application,
        leastRecentlyUsedCacheEvictor: LeastRecentlyUsedCacheEvictor,
        databaseProvider: DatabaseProvider,
    ): SimpleCache {
        return SimpleCache(application.cacheDir, leastRecentlyUsedCacheEvictor, databaseProvider)
    }

}