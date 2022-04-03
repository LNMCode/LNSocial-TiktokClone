package com.longnp.lnsocial.business.datasource.network.auth

import com.google.gson.annotations.SerializedName

class ProfileData(
    @SerializedName("userid")
    val pk: String, //id userid

    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("nickname")
    val nickName: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("avatar_link")
    val avatarLink: String,

    @SerializedName("number_followers")
    val numberFollowers: Int,

    @SerializedName("number_following")
    val numberFollowing: Int,

    @SerializedName("number_like")
    val numberLike: Int,

    @SerializedName("followers")
    val followers: List<String>,

    @SerializedName("following")
    val following: List<String>,

    @SerializedName("public_videos")
    val publicVideos: List<String>,

    @SerializedName("favorite_videos")
    val favoriteVideos: List<String>,

    @SerializedName("favorite_sounds")
    val favoriteSounds: List<String>,

    @SerializedName("comments")
    val comments: List<String>,

    @SerializedName("my_videos")
    val myVideos: List<String>,

    @SerializedName("cared")
    val cared: String,

    @SerializedName("cared_recomm")
    val caredRecommend: String,

    @SerializedName("message")
    val message: List<String>
)