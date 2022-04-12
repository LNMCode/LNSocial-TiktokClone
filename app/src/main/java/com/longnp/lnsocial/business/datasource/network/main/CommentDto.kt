package com.longnp.lnsocial.business.datasource.network.main

import com.google.gson.annotations.SerializedName
import com.longnp.lnsocial.business.domain.models.Comment

data class CommentDto(
    @SerializedName("id")
    val id: String, // id comment,

    @SerializedName("videoid")
    val videoId: String,

    @SerializedName("number_like")
    val numberLike: Int,

    @SerializedName("name_user")
    val nameUser: String,

    @SerializedName("avatar_link")
    val avatarLink: String,

    @SerializedName("comment")
    val comment: String,

    @SerializedName("order")
    val order: Int,
)

fun CommentDto.toComment(): Comment {
    return Comment(
        id = id,
        videoId = videoId,
        numberLike = numberLike,
        nameUser = nameUser,
        avatarLink = avatarLink,
        comment = comment,
        order = order,
    )
}