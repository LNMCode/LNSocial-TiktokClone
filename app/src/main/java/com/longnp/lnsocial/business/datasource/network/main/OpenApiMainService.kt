package com.longnp.lnsocial.business.datasource.network.main

import com.longnp.lnsocial.business.datasource.network.inbox.response.FriendDto
import com.longnp.lnsocial.business.datasource.network.main.response.ResponseModel
import com.longnp.lnsocial.business.domain.models.inbox.Friend
import okhttp3.RequestBody
import retrofit2.http.*

interface OpenApiMainService {

    @Headers("Content-Type: application/json")
    @POST("video/seeds")
    suspend fun getVideoSeeds(
        @Body params: RequestBody
    ): ResponseModel

    @GET("profile/getall")
    suspend fun getAllProfile(): List<FriendDto>

}