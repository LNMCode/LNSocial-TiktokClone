package com.longnp.lnsocial.business.interactors.auth

import android.util.Log
import com.longnp.lnsocial.business.datasource.cache.account.AccountDao
import com.longnp.lnsocial.business.datasource.cache.account.toEntity
import com.longnp.lnsocial.business.datasource.cache.auth.AuthTokenDao
import com.longnp.lnsocial.business.datasource.cache.auth.toEntity
import com.longnp.lnsocial.business.datasource.cache.profile.ProfileDao
import com.longnp.lnsocial.business.datasource.cache.profile.toEntity
import com.longnp.lnsocial.business.datasource.datastore.AppDataStore
import com.longnp.lnsocial.business.datasource.network.auth.OpenApiAuthService
import com.longnp.lnsocial.business.domain.models.Account
import com.longnp.lnsocial.business.domain.models.AuthToken
import com.longnp.lnsocial.business.domain.models.Profile
import com.longnp.lnsocial.business.domain.util.Constants
import com.longnp.lnsocial.business.domain.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class Register(
    private val service: OpenApiAuthService,
    private val accountDao: AccountDao,
    private val authTokenDao: AuthTokenDao,
    private val profileDao: ProfileDao,
    private val appDataStoreManager: AppDataStore,
) {
    fun execute(
        username: String,
        password: String,
    ): Flow<DataState<AuthToken>> = flow {
        emit(DataState.loading<AuthToken>())
        val paramsRequestBody = Constants.getParamsBodyAuth(
            hashMapOf("username" to username, "password" to password, "scope" to "1")
        )
        val bodyRequest = Constants.getRequestBodyAuth(paramsRequestBody.toString())
        val register = service.register(bodyRequest)
        if (register.message == "Username is exist!") {
            throw Exception(register.message)
        }
        delay(2000)
        val login = service.login(params = bodyRequest)
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

        // take profile
        val paramsRequestBodyProfile = Constants.getParamsBodyAuth(
            hashMapOf("userid" to login.userid, "access_token" to login.accessToken)
        )
        val bodyRequestProfile = Constants.getRequestBodyAuth(paramsRequestBodyProfile.toString())
        val profile = service.profile(bodyRequestProfile).data
        profileDao.insertAndReplace(
            Profile(
                pk = profile.pk,
                accessToken = profile.accessToken,
                nickName = profile.nickName,
                description = profile.description,
                avatarLink = profile.avatarLink,
                numberFollowers = profile.numberFollowers,
                numberFollowing = profile.numberFollowing,
                numberLike = profile.numberLike,
                followers = profile.followers,
                following = profile.following,
                publicVideos = profile.publicVideos,
                favoriteVideos = profile.favoriteVideos,
                favoriteSounds = profile.favoriteSounds,
                comments = profile.comments,
                myVideos = profile.myVideos,
                cared = profile.cared,
                caredRecommend = profile.caredRecommend,
                message = profile.message
            ).toEntity()
        )
        emit(DataState.data(response = null, data = authToken))
    }.catch { e ->
        Log.d("TAG", "execute register: " + e.message)
    }
}