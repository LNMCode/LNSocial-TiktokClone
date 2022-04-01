package com.longnp.lnsocial.business.interactors.auth

import android.util.Log
import com.longnp.lnsocial.business.datasource.cache.account.AccountDao
import com.longnp.lnsocial.business.datasource.cache.account.toEntity
import com.longnp.lnsocial.business.datasource.cache.auth.AuthTokenDao
import com.longnp.lnsocial.business.datasource.cache.auth.toEntity
import com.longnp.lnsocial.business.datasource.datastore.AppDataStore
import com.longnp.lnsocial.business.datasource.network.auth.OpenApiAuthService
import com.longnp.lnsocial.business.domain.models.Account
import com.longnp.lnsocial.business.domain.models.AuthToken
import com.longnp.lnsocial.business.domain.util.Constants
import com.longnp.lnsocial.business.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.json.JSONObject

class Login(
    private val service: OpenApiAuthService,
    private val accountDao: AccountDao,
    private val authTokenDao: AuthTokenDao,
    private val appDataStoreManager: AppDataStore,
) {
    fun execute(
        username: String,
        password: String
    ): Flow<DataState<AuthToken>> = flow {
        emit(DataState.loading<AuthToken>())
        val paramsRequestBody = JSONObject()
        paramsRequestBody.put("username",username)
        paramsRequestBody.put("password", password)
        val bodyRequest = Constants.getRequestBodyAuth(paramsRequestBody.toString())
        val login = service.login(bodyRequest)
        if (login.message == "Password is correct" || login.message == "User is not exist") {
            throw Exception(login.message)
        }
        accountDao.insertOrIgnore(
            Account(
                pk = login.userid,
                username = login.username,
                scope = login.scope
            ).toEntity()
        )

        val authToken = AuthToken(
            accountPk = login.userid,
            token = login.accessToken,
            authProfileId = login.authProfileId,
        )

        val result = authTokenDao.insert(authToken = authToken.toEntity())
        // can't proceed unless token can be cached
        if(result < 0){
            throw Exception("ERROR_SAVE_AUTH_TOKEN")
        }

        appDataStoreManager.setValue("DataStoreKeys.PREVIOUS_AUTH_USER", username)
        emit(DataState.data(response = null, data = authToken))

    }.catch { e->
        Log.d("TAG", "execute login: " + e.message)
    }
}