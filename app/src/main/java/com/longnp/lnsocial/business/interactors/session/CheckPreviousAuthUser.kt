package com.longnp.lnsocial.business.interactors.session

import com.longnp.lnsocial.business.datasource.cache.account.AccountDao
import com.longnp.lnsocial.business.datasource.cache.auth.AuthTokenDao
import com.longnp.lnsocial.business.datasource.cache.auth.toAuthToken
import com.longnp.lnsocial.business.domain.models.AuthToken
import com.longnp.lnsocial.business.domain.util.DataState
import com.longnp.lnsocial.business.domain.util.MessageType
import com.longnp.lnsocial.business.domain.util.Response
import com.longnp.lnsocial.business.domain.util.UIComponentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class CheckPreviousAuthUser(
    private val accountDao: AccountDao,
    private val authTokenDao: AuthTokenDao,
) {

    fun execute(
        username: String
    ): Flow<DataState<AuthToken>> = flow {
        emit(DataState.loading())
        var authToken: AuthToken? = null
        val entity = accountDao.searchByUsername(username)
        if (entity != null) {
            authToken = authTokenDao.searchByPk(entity.pk)?.toAuthToken()
            if (authToken != null) {
                emit(DataState.data(response = null, data = authToken))
            }
        }
        if(authToken == null){
            throw Exception("ERROR_NO_PREVIOUS_AUTH_USER")
        }
    }.catch{ e ->
        e.printStackTrace()
        emit(returnNoPreviousAuthUser())
    }

    /**
     * If no user was previously authenticated then emit this error. The UI is waiting for it.
     */
    private fun returnNoPreviousAuthUser(): DataState<AuthToken> {
        return DataState.error<AuthToken>(
            response = Response(
                "SuccessHandling.RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE",
                UIComponentType.None(),
                MessageType.Error()
            )
        )
    }

}