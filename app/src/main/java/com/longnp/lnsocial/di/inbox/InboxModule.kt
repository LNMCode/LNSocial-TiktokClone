package com.longnp.lnsocial.di.inbox

import com.longnp.lnsocial.business.domain.models.inbox.Friend
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
    ): GetFriendRecommend {
        return GetFriendRecommend(dataRepoJson = dataRepoJson)
    }
}