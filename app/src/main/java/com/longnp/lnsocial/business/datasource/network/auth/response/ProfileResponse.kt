package com.longnp.lnsocial.business.datasource.network.auth.response

import com.google.gson.annotations.SerializedName
import com.longnp.lnsocial.business.datasource.network.auth.ProfileData

class ProfileResponse(
    @SerializedName("status")
    var status: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("type")
    var type: Int,

    @SerializedName("data")
    var data: ProfileData
)