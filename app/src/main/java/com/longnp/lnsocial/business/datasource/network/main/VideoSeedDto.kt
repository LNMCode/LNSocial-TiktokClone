package com.longnp.lnsocial.business.datasource.network.main

import com.google.gson.annotations.SerializedName
import com.longnp.lnsocial.business.domain.models.VideoSeed

class VideoSeedDto(
    @SerializedName("id")
    val pk: String, // id

    @SerializedName("video_link")
    val videoLink: String,

    @SerializedName("hashtags_video")
    val hashTagVideo: String,

    @SerializedName("sound_id")
    val soundId: String,

    @SerializedName("sound_title")
    val soundTitle: String,

    @SerializedName("sound_thumbnail")
    val soundThumbnail: String,

    @SerializedName("auth_profile_id")
    val authProfileId: String,

    @SerializedName("number_like")
    val numberLike: String,

    @SerializedName("number_comments")
    val numberComments: String,

    @SerializedName("number_share")
    val numberShared: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("types_video")
    val typesVideos: String,

    @SerializedName("nickname")
    val nickName: String,

    @SerializedName("avatar_link")
    val avatarLink: String
)

fun VideoSeedDto.toVideo(): VideoSeed {
    return VideoSeed(
        pk = pk,
        videoLink = videoLink,
        hashTagVideo = hashTagVideo,
        soundId = soundId,
        soundTitle = soundTitle,
        soundThumbnail = soundThumbnail,
        authProfileId = authProfileId,
        numberLike = numberLike,
        numberComments = numberComments,
        numberShared = numberShared,
        description = description,
        typesVideos = typesVideos,
        nickName = nickName,
        avatarLink = avatarLink,
    )
}