package com.longnp.lnsocial.di.auth

import com.longnp.lnsocial.business.datasource.cache.account.AccountDao
import com.longnp.lnsocial.business.datasource.cache.auth.AuthTokenDao
import com.longnp.lnsocial.business.datasource.datastore.AppDataStore
import com.longnp.lnsocial.business.datasource.network.auth.OpenApiAuthService
import com.longnp.lnsocial.business.interactors.auth.Login
import com.longnp.lnsocial.business.interactors.session.CheckPreviousAuthUser
import com.longnp.lnsocial.business.interactors.session.Logout
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.FlowPreview
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule{

    @Singleton
    @Provides
    fun provideOpenApiAuthService(retrofitBuilder: Retrofit.Builder): OpenApiAuthService {
        return retrofitBuilder
            .build()
            .create(OpenApiAuthService::class.java)
    }

    @Singleton
    @Provides
    fun provideCheckPrevAuthUser(
        accountDao: AccountDao,
        authTokenDao: AuthTokenDao,
    ): CheckPreviousAuthUser {
        return CheckPreviousAuthUser(
            accountDao,
            authTokenDao
        )
    }

    @Singleton
    @Provides
    fun provideLogin(
        service: OpenApiAuthService,
        accountDao: AccountDao,
        authTokenDao: AuthTokenDao,
        appDataStoreManager: AppDataStore,
    ): Login {
        return Login(
            service,
            accountDao,
            authTokenDao,
            appDataStoreManager
        )
    }

    @Singleton
    @Provides
    fun provideLogout(
        authTokenDao: AuthTokenDao,
    ): Logout {
        return Logout(authTokenDao)
    }
/*
    @Singleton
    @Provides
    fun provideRegister(
        service: OpenApiAuthService,
        accountDao: AccountDao,
        authTokenDao: AuthTokenDao,
        appDataStoreManager: AppDataStore,
    ): Register {
        return Register(
            service,
            accountDao,
            authTokenDao,
            appDataStoreManager
        )
    }*/
}









