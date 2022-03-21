package com.longnp.lnsocial.business.datasource.network.main

import com.google.gson.annotations.SerializedName
import com.longnp.lnsocial.business.domain.models.VideoSeed

class VideoSeedDto(
    @SerializedName("id")
    val pk: Int, // id

    @SerializedName("video_link")
    val videoLink: String,

    @SerializedName("hashtags_video")
    val hashTagVideo: String,

    @SerializedName("sound_id")
    val soundId: String,

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
    val types_videos: String,
)

fun VideoSeedDto.toVideo(): VideoSeed {
    return VideoSeed(
        pk = pk,
        videoLink = videoLink,
        hashTagVideo = hashTagVideo,
        soundId = soundId,
        authProfileId = authProfileId,
        numberLike = numberLike,
        numberComments = numberComments,
        numberShared = numberShared,
        description = description,
        types_videos = types_videos,
    )
}