package com.longnp.lnsocial.business.datasource.network.auth

import com.longnp.lnsocial.business.datasource.network.auth.response.LoginResponse
import com.longnp.lnsocial.business.datasource.network.auth.response.ProfileResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenApiAuthService {

    @Headers("Content-Type: application/json")
    @POST("user/login")
    suspend fun login(
        @Body params: RequestBody
    ): LoginResponse

    @Headers("Content-Type: application/json")
    @POST("user/register")
    suspend fun register(
        @Body params: RequestBody
    ): LoginResponse

    @Headers("Content-Type: application/json")
    @POST("profile/getByUserId")
    suspend fun profile(
        @Body params: RequestBody
    ): ProfileResponse
}