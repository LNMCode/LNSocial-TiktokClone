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

    @Headers("Content-Type: application/json")
    @POST("profile/getfriendcommend")
    suspend fun getFriendCommend(
        @Body params: RequestBody
    ): List<FriendDto>

    @Headers("Content-Type: application/json")
    @POST("profile/connectmessage")
    suspend fun addFriendMessage(
        @Body params: RequestBody
    ): InboxModelDto

    @Headers("Content-Type: application/json")
    @POST("profile/getallconnectedmessage")
    suspend fun getFriendMessage(
        @Body params: RequestBody
    ): List<InboxModelDto>

    @Headers("Content-Type: application/json")
    @POST("video/getvideosbytypeanduserid")
    suspend fun getVideosByTypeAndUserId(
        @Body params: RequestBody
    ): VideoSeedResponse

}