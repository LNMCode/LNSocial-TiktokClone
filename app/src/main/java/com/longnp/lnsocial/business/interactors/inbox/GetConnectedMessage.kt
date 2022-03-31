package com.longnp.lnsocial.business.interactors.inbox

import android.util.Log
import com.longnp.lnsocial.business.datasource.network.inbox.response.toInboxModel
import com.longnp.lnsocial.business.datasource.network.main.OpenApiMainService
import com.longnp.lnsocial.business.domain.models.inbox.InboxModel
import com.longnp.lnsocial.business.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody
import java.lang.Exception

class GetConnectedMessage(
    private val service: OpenApiMainService,
) {
    fun execute(
        body: RequestBody
    ): Flow<DataState<List<InboxModel>>> = flow {
        emit(DataState.loading())
        try {
            val data = service.getFriendMessage(body)
            emit(
                DataState.data(
                    response = null,
                    data = data.map { it.toInboxModel() }
                )
            )
        } catch (e: Exception) {
            Log.d("TAG", "GetFriendBug " + e.message)
        }
    }
}