package com.longnp.lnsocial.business.datasource.cache.profile

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile WHERE pk = :pk")
    suspend fun searchByPk(pk: String): ProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAndReplace(profileEntity: ProfileEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnore(profileEntity: ProfileEntity): Long

    @Query("""
        UPDATE profile SET favorite_videos = :favorite_videos
        WHERE pk = :pk
        """)
    suspend fun updateFavoriteVideos(pk: String, favorite_videos: List<String>)

    @Query("""
        UPDATE profile SET following = :following, number_following = :number
        WHERE pk = :pk
        """)
    suspend fun updateListFollowing(pk: String, following: List<String>, number: Int)

    @Query("""
        UPDATE profile SET nickname = :username WHERE pk = :pk
    """)
    suspend fun updateUsername(pk: String, username: String)
}