package com.longnp.lnsocial.business.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideoSeed(
    val pk: String, // id
    val videoLink: String,
    val hashTagVideo: String,
    val soundId: String,
    val soundTitle: String,
    val soundThumbnail: String,
    val authProfileId: String,
    val numberLike: Int,
    val numberComments: Int,
    val numberShared: Int,
    val description: String,
    val typesVideos: String,
    val nickName: String,
    val avatarLink: String,
    val thumbnail: String,
): Parcelable