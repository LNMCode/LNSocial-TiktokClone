package com.longnp.lnsocial.business.datasource.network.main

import com.longnp.lnsocial.business.datasource.network.inbox.response.FriendDto
import com.longnp.lnsocial.business.datasource.network.inbox.response.InboxModelDto
import com.longnp.lnsocial.business.datasource.network.main.response.VideoSeedResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface OpenApiMainService {

    @Headers("Content-Type: application/json")
    @POST("video/seeds")
    suspend fun getVideoSeeds(
        @Body params: RequestBody
    ): VideoSeedResponse

    @POST("profile/getfriendcommend")
    suspend fun getFriendCommend(
        @Body params: RequestBody
    ): List<FriendDto>

    @POST("profile/connectmessage")
    suspend fun addFriendMessage(
        @Body params: RequestBody
    ): InboxModelDto

    @POST("profile/getallconnectedmessage")
    suspend fun getFriendMessage(
        @Body params: RequestBody
    ): List<InboxModelDto>

}