package com.longnp.lnsocial.business.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideoSeed(
    val pk: Int, // id
    val videoLink: String,
    val hashTagVideo: String,
    val soundId: String,
    val authProfileId: String,
    val numberLike: String,
    val numberComments: String,
    val numberShared: String,
    val description: String,
    val types_videos: String,
): Parcelable