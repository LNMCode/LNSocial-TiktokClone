package com.longnp.lnsocial.business.interactors.session

import com.longnp.lnsocial.business.datasource.cache.auth.AuthTokenDao
import com.longnp.lnsocial.business.domain.util.DataState
import com.longnp.lnsocial.business.domain.util.MessageType
import com.longnp.lnsocial.business.domain.util.Response
import com.longnp.lnsocial.business.domain.util.UIComponentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class Logout(
    private val authTokenDao: AuthTokenDao,
) {
    fun execute(): Flow<DataState<Response>> = flow {
        emit(DataState.loading<Response>())
        authTokenDao.clearTokens()
        emit(
            DataState.data<Response>(
                data = Response(
                    message = "SUCCESS_LOGOUT",
                    uiComponentType = UIComponentType.Dialog(),
                    messageType = MessageType.Error()
                ),
                response = null,
            )
        )
    }.catch { e ->
        e.printStackTrace()
        emit(
            DataState.error<Response>(
                response = Response(
                    message = e.message,
                    uiComponentType = UIComponentType.Dialog(),
                    messageType = MessageType.Error()
                )
            )
        )
    }
}