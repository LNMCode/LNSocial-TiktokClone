package com.longnp.lnsocial.business.interactors.inbox

import android.util.Log
import com.longnp.lnsocial.business.datasource.network.inbox.response.toInboxModel
import com.longnp.lnsocial.business.datasource.network.main.OpenApiMainService
import com.longnp.lnsocial.business.domain.models.inbox.InboxModel
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
        body: RequestBody
    ): Flow<DataState<InboxModel>> = flow {
        emit(DataState.loading<InboxModel>())
        val data = service.addFriendMessage(body)
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