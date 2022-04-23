package com.longnp.lnsocial.business.datasource.network.create

import com.google.gson.annotations.SerializedName
import com.longnp.lnsocial.business.domain.models.PostVideo

data class PostVideoDto(
    @SerializedName("message")
    var message: String,
)

fun PostVideoDto.toPostVideo(): PostVideo {
    return PostVideo(
        message = message
    )
}