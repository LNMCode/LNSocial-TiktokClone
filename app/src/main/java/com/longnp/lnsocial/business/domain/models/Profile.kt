package com.longnp.lnsocial.business.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Profile(
    val pk: String, //id userid
    val accessToken: String,
    val nickName: String,
    val description: String,
    val avatarLink: String,
    val numberFollowers: Int,
    val numberFollowing: Int,
    val numberLike: Int,
    val followers: List<String>,
    val following: List<String>,
    val publicVideos: List<String>,
    val favoriteVideos: List<String>,
    val favoriteSounds: List<String>,
    val comments: List<String>,
    val myVideos: List<String>,
    val cared: String,
    val caredRecommend: String,
    val message: List<String>
): Parcelable