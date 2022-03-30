package com.longnp.lnsocial.business.datasource.network.inbox.response

import com.google.gson.annotations.SerializedName
import com.longnp.lnsocial.business.domain.models.inbox.Friend

data class FriendDto(

    @SerializedName("idauth")
    val id: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("avatar")
    val avatar: String

)

fun FriendDto.toFriend(): Friend {
    return Friend(
        id = id,
        username = username,
        avatar = avatar,
    )
}