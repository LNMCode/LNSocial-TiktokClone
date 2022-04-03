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
import org.json.JSONObject
import java.lang.Exception

class GetConnectedMessage(
    private val service: OpenApiMainService,
) {
    fun execute(
        authToken: AuthToken?,
    ): Flow<DataState<List<InboxModel>>> = flow {
        emit(DataState.loading())
        if (authToken == null) {
            throw Exception("GetConnectedMessage: Auth token is null")
        }
        val paramsRequestBody = getParamsBodyAuth(
            hashMapOf(
                "userid" to authToken.accountPk,
                "access_token" to authToken.token,
                "auth_profile_id" to authToken.authProfileId
            )
        )
        val bodyRequest = getRequestBodyAuth(paramsRequestBody.toString())
        val data = service.getFriendMessage(bodyRequest)
        emit(
            DataState.data(
                response = null,
                data = data.map { it.toInboxModel() }
            )
        )
    }.catch { e ->
        Log.d("TAG", "GetFriendBug " + e.message)
    }
}