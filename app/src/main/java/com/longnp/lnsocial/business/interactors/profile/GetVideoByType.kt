package com.longnp.lnsocial.business.interactors.profile

import android.util.Log
import com.longnp.lnsocial.business.datasource.network.main.OpenApiMainService
import com.longnp.lnsocial.business.datasource.network.main.toVideo
import com.longnp.lnsocial.business.domain.models.AuthToken
import com.longnp.lnsocial.business.domain.models.VideoSeed
import com.longnp.lnsocial.business.domain.util.Constants
import com.longnp.lnsocial.business.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class GetVideoByType(
    private val service: OpenApiMainService,
) {
    fun execute(
        authToken: AuthToken?,
        type: String,
    ): Flow<DataState<List<VideoSeed>>> = flow{
        emit(DataState.loading<List<VideoSeed>>())
        if (authToken == null) {
            throw Exception("SearchVideoSeeds: Auth token is null")
        }
        val paramsRequestBody = Constants.getParamsBodyAuth(
            hashMapOf(
                "userid" to authToken.accountPk,
                "access_token" to authToken.token,
                "type" to type.lowercase()
            )
        )
        val bodyRequest = Constants.getRequestBodyAuth(paramsRequestBody.toString())
        val data = service.getVideosByTypeAndUserId(bodyRequest).data
        emit(DataState.data(response = null, data =  data.map { it.toVideo() }))
    }.catch { e ->
        Log.d("TAG", "GetFriendBug " + e.message)
    }
}