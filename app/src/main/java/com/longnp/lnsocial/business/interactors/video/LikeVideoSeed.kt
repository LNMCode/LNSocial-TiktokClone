package com.longnp.lnsocial.business.interactors.video

import android.util.Log
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

class LikeVideoSeed(
    private val service: OpenApiMainService
) {
    fun execute(
        pk: String, // id video
        authToken: AuthToken,
    ): Flow<DataState<VideoSeed>> = flow {
        emit(DataState.loading<VideoSeed>())
        val paramsRequestBody = Constants.getParamsBodyAuth(
            hashMapOf(
                "userid" to authToken.accountPk,
                "access_token" to authToken.token,
                "auth_profile_id" to authToken.authProfileId,
                "videoid" to pk,
            )
        )
        val bodyRequest = Constants.getRequestBodyAuth(paramsRequestBody.toString())
        val data = service.likeVideoSeed(params = bodyRequest)
        emit(DataState.data(response = null, data = data.toVideo()))
    }.catch { e ->
        Log.d("TAG", "execute: ${e.message}")
    }
}