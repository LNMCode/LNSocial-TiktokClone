package com.longnp.lnsocial.business.datasource.cache.profile

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.longnp.lnsocial.business.domain.models.Profile

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "pk")
    val pk: String, //id userid

    @ColumnInfo(name = "access_token")
    val accessToken: String,

    @ColumnInfo(name = "nickname")
    val nickName: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "avatar_link")
    val avatarLink: String,

    @ColumnInfo(name = "number_followers")
    val numberFollowers: Int,

    @ColumnInfo(name = "number_following")
    val numberFollowing: Int,

    @ColumnInfo(name = "number_like")
    val numberLike: Int,

    @ColumnInfo(name = "followers")
    val followers: List<String>,

    @ColumnInfo(name = "following")
    val following: List<String>,

    @ColumnInfo(name = "public_videos")
    val publicVideos: List<String>,

    @ColumnInfo(name = "favorite_videos")
    val favoriteVideos: List<String>,

    @ColumnInfo(name = "favorite_sounds")
    val favoriteSounds: List<String>,

    @ColumnInfo(name = "comments")
    val comments: List<String>,

    @ColumnInfo(name = "my_videos")
    val myVideos: List<String>,

    @ColumnInfo(name = "cared")
    val cared: String,

    @ColumnInfo(name = "cared_recommend")
    val caredRecommend: String,

    @ColumnInfo(name = "message")
    val message: List<String>
)

fun ProfileEntity.toProfile(): Profile {
    return Profile(
        pk = pk,
        accessToken = accessToken,
        nickName = nickName,
        description = description,
        avatarLink = avatarLink,
        numberFollowers = numberFollowers,
        numberFollowing = numberFollowing,
        numberLike = numberLike,
        followers = followers,
        following = following,
        publicVideos = publicVideos,
        favoriteVideos = favoriteVideos,
        favoriteSounds = favoriteSounds,
        comments = comments,
        myVideos = myVideos,
        cared = cared,
        caredRecommend = caredRecommend,
        message = message
    )
}

fun Profile.toEntity(): ProfileEntity {
    return ProfileEntity(
        pk = pk,
        accessToken = accessToken,
        nickName = nickName,
        description = description,
        avatarLink = avatarLink,
        numberFollowers = numberFollowers,
        numberFollowing = numberFollowing,
        numberLike = numberLike,
        followers = followers,
        following = following,
        publicVideos = publicVideos,
        favoriteVideos = favoriteVideos,
        favoriteSounds = favoriteSounds,
        comments = comments,
        myVideos = myVideos,
        cared = cared,
        caredRecommend = caredRecommend,
        message = message
    )
}