package com.longnp.lnsocial.business.datasource.network.inbox.response

import com.google.gson.annotations.SerializedName
import com.longnp.lnsocial.business.domain.models.inbox.Friend

data class FriendDto(

    @SerializedName("id")
    val id: String, // id of friend

    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("idauth")
    val idAuth: String, // id profile user

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