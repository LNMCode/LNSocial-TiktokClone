package com.longnp.lnsocial.business.interactors.video

import android.util.Log
import com.longnp.lnsocial.business.datasource.cache.profile.ProfileDao
import com.longnp.lnsocial.business.datasource.network.main.OpenApiMainService
import com.longnp.lnsocial.business.datasource.network.main.toSeedItem
import com.longnp.lnsocial.business.datasource.network.main.toVideo
import com.longnp.lnsocial.business.domain.models.AuthToken
import com.longnp.lnsocial.business.domain.models.SeedItem
import com.longnp.lnsocial.business.domain.models.VideoSeed
import com.longnp.lnsocial.business.domain.util.Constants
import com.longnp.lnsocial.business.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class FollowUser(
    private val service: OpenApiMainService,
    private val profileDao: ProfileDao,
) {
    fun execute(
        userFollow: String, // id user orther
        followingList: List<String>,
        authToken: AuthToken,
    ): Flow<DataState<SeedItem>> = flow {
        emit(DataState.loading<SeedItem>())
        val paramsRequestBody = Constants.getParamsBodyAuth(
            hashMapOf(
                "userid" to authToken.accountPk,
                "access_token" to authToken.token,
                "auth_profile_id" to authToken.authProfileId,
                "userfollow" to userFollow,
            )
        )
        val bodyRequest = Constants.getRequestBodyAuth(paramsRequestBody.toString())
        val data = service.followUser(params = bodyRequest)
        profileDao.updateListFollowing(
            pk = authToken.authProfileId,
            following = followingList + userFollow,
            number = followingList.size + 1,
        )
        emit(DataState.data(response = null, data = data.toSeedItem()))
    }.catch { e ->
        Log.d("TAG", "execute: ${e.message}")
    }
}