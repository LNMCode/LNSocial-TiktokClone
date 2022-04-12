package com.longnp.lnsocial.business.domain.models

data class Comment(
    val id: String, // id comment,
    val videoId: String, // id of video
    val numberLike: Int,
    val nameUser: String,
    val avatarLink: String,
    val comment: String,
    val order: Int,
)