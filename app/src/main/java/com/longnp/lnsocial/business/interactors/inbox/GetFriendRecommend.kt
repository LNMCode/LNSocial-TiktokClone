package com.longnp.lnsocial.business.interactors.inbox

import android.util.Log
import androidx.annotation.RawRes
import com.longnp.lnsocial.business.datasource.network.inbox.response.toFriend
import com.longnp.lnsocial.business.datasource.network.main.OpenApiMainService
import com.longnp.lnsocial.business.domain.models.AuthToken
import com.longnp.lnsocial.business.domain.models.inbox.Friend
import com.longnp.lnsocial.business.domain.util.Constants
import com.longnp.lnsocial.business.domain.util.Constants.Companion.getParamsBodyAuth
import com.longnp.lnsocial.business.domain.util.Constants.Companion.getRequestBodyAuth
import com.longnp.lnsocial.business.domain.util.DataState
import com.longnp.lnsocial.presentation.main.inbox.addfriend.FriendDataRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody
import java.lang.Exception

class GetFriendRecommend(
    private val dataRepoJson: FriendDataRepo,
    private val service: OpenApiMainService,
) {
    /*fun execute(
        @RawRes id: Int,
    ): Flow<DataState<List<Friend>>> = flow {
        emit(DataState.loading())
        val data = dataRepoJson.getStoriesData(id)
        emit(DataState.data(response = null, data = data))
    }.catch { e ->
        Log.d("TAG", "GetFriendBug " + e.message)
    }*/

    fun execute(
        authToken: AuthToken?,
    ): Flow<DataState<List<Friend>>> = flow {
        emit(DataState.loading())
        if (authToken == null) {
            throw Exception("GetFriendRecommend: Auth token is null")
        }
        val paramsRequestBody = getParamsBodyAuth(
            hashMapOf(
                "userid" to authToken.accountPk,
                "access_token" to authToken.token,
                "auth_profile_id" to authToken.authProfileId
            )
        )
        val bodyRequest = getRequestBodyAuth(paramsRequestBody.toString())
        val data = service.getFriendCommend(bodyRequest)
        emit(DataState.data(response = null, data = data.map { it.toFriend() }))
    }.catch { e ->
        Log.d("TAG", "GetFriendBug Request api" + e.message)
    }
}