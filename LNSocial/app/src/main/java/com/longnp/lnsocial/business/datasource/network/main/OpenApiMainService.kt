package com.longnp.lnsocial.business.datasource.network.main

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenApiMainService {
    @GET("video/seeds")
    suspend fun getVideoSeeds(
        @Body params: RequestBody
    ): List<VideoSeedDto>

}