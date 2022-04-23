package com.longnp.lnsocial.business.datasource.network.main

import com.longnp.lnsocial.business.datasource.network.create.PostVideoDto
import com.longnp.lnsocial.business.datasource.network.inbox.response.FriendDto
import com.longnp.lnsocial.business.datasource.network.inbox.response.InboxModelDto
import com.longnp.lnsocial.business.datasource.network.main.response.SeedItemCommentsResponse
import com.longnp.lnsocial.business.datasource.network.main.response.VideoSeedResponse
import com.longnp.lnsocial.business.domain.models.SeedItem
import com.longnp.lnsocial.business.domain.models.VideoSeed
import okhttp3.MultipartBody
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

    @Headers("Content-Type: application/json")
    @POST("video/likevideo")
    suspend fun likeVideoSeed(
        @Body params: RequestBody
    ): VideoSeedDto

    @Headers("Content-Type: application/json")
    @POST("video/followuser")
    suspend fun followUser(
        @Body params: RequestBody
    ): SeedItemDto

    @Headers("Content-Type: application/json")
    @POST("video/getcommentsvideo")
    suspend fun getCommentsVideo(
        @Body params: RequestBody
    ):  List<CommentDto>

    @Headers("Content-Type: application/json")
    @POST("video/commentvideo")
    suspend fun commentVideo(
        @Body params: RequestBody
    ): List<CommentDto>

    @Multipart
    @POST("video/add")
    suspend fun postVideo(
        @Part video: MultipartBody.Part,
        @Part("access_token") accessToken: String,
        @Part("iduser") idUser: String,
        @Part("type") type: String,
        @Part("hashtags_video") hashTagsVideo: String,
        @Part("sound_id") soundId: String,
        @Part("auth_profile_id") authProfileId: String,
        @Part("number_like") numberLike: Int = 0,
        @Part("number_comments") numberComment: Int = 0,
        @Part("number_share") numberShare: Int = 0,
        @Part("description") description: String,
    ): PostVideoDto
}