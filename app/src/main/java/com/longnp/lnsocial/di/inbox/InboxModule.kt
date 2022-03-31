package com.longnp.lnsocial.di.inbox

import com.longnp.lnsocial.business.datasource.network.main.OpenApiMainService
import com.longnp.lnsocial.business.domain.models.inbox.Friend
import com.longnp.lnsocial.business.interactors.inbox.AddFriendMessage
import com.longnp.lnsocial.business.interactors.inbox.GetConnectedMessage
import com.longnp.lnsocial.business.interactors.inbox.GetFriendRecommend
import com.longnp.lnsocial.presentation.main.inbox.addfriend.FriendDataRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InboxModule {

    @Provides
    @Singleton
    fun provideGetFriendRecommend(
        dataRepoJson: FriendDataRepo,
        service: OpenApiMainService,
        ): GetFriendRecommend {
        return GetFriendRecommend(
            dataRepoJson = dataRepoJson,
            service = service
        )
    }

    @Provides
    @Singleton
    fun provideAddFriendMessage(
        service: OpenApiMainService
    ): AddFriendMessage {
        return AddFriendMessage(
            service = service,
        )
    }

    @Provides
    @Singleton
    fun provideGetConnectedMessage(
        service: OpenApiMainService
    ): GetConnectedMessage {
        return GetConnectedMessage(
            service = service,
        )
    }
}