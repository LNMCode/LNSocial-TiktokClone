package com.longnp.lnsocial.business.datasource.network.auth.response

import com.google.gson.annotations.SerializedName

class LoginResponse(
    @SerializedName("id")
    val userid: String,

    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("auth_profile_id")
    val authProfileId: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("scope")
    val scope: String,

    @SerializedName("message")
    val message: String,
)