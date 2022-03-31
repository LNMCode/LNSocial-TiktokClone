package com.longnp.lnsocial.business.datasource.network.inbox.response

import com.google.gson.annotations.SerializedName
import com.longnp.lnsocial.business.datasource.network.main.VideoSeedDto

data class AddFriendDto(
    @SerializedName("status")
    var status: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("type")
    var type: Int,

    @SerializedName("data")
    var data: List<VideoSeedDto>
)