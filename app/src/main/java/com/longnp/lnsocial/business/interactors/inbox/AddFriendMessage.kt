package com.longnp.lnsocial.business.interactors.inbox

import android.util.Log
import com.longnp.lnsocial.business.datasource.network.inbox.response.toInboxModel
import com.longnp.lnsocial.business.datasource.network.main.OpenApiMainService
import com.longnp.lnsocial.business.domain.models.AuthToken
import com.longnp.lnsocial.business.domain.models.inbox.InboxModel
import com.longnp.lnsocial.business.domain.util.Constants
import com.longnp.lnsocial.business.domain.util.Constants.Companion.getParamsBodyAuth
import com.longnp.lnsocial.business.domain.util.Constants.Companion.getRequestBodyAuth
import com.longnp.lnsocial.business.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody
import java.lang.Exception

class AddFriendMessage(
    private val service: OpenApiMainService,
) {
    fun execute(
        authToken: AuthToken?,
        authProfileIdOther: String,
        username: String,
        ava: String,
    ): Flow<DataState<InboxModel>> = flow {
        emit(DataState.loading<InboxModel>())
        if (authToken == null) {
            throw Exception("AddFriendMessage: Auth token is null")
        }
        val paramsRequestBody = getParamsBodyAuth(
            hashMapOf(
                "userid" to authToken.accountPk,
                "access_token" to authToken.token,
                "auth_profile_id" to authToken.authProfileId,
                "auth_profile_id_other" to authProfileIdOther,
                "username" to username,
                "ava" to ava,
            )
        )
        val bodyRequest = getRequestBodyAuth(paramsRequestBody.toString())
        val data = service.addFriendMessage(bodyRequest)
        emit(
            DataState.data(
                response = null,
                data = data.toInboxModel()
            )
        )
    }.catch {
        Log.d("TAG", "GetFriendBug ")
    }
}