package com.longnp.lnsocial.business.datasource.network.auth

import com.longnp.lnsocial.business.datasource.network.auth.response.LoginResponse
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

}