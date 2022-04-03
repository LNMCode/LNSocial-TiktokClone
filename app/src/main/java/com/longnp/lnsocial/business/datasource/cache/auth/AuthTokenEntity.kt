package com.longnp.lnsocial.business.datasource.cache.auth

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.longnp.lnsocial.business.datasource.cache.account.AccountEntity
import com.longnp.lnsocial.business.domain.models.AuthToken
import java.lang.Exception

@Entity(
    tableName = "auth_token",
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["pk"],
            childColumns = ["account_pk"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AuthTokenEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "account_pk")
    var account_pk: String, // is userid from accountEntity

    @ColumnInfo(name = "access_token")
    val token: String? = null,

    @ColumnInfo(name = "auth_profile_id")
    val authProfileId: String? = null
)

fun AuthTokenEntity.toAuthToken(): AuthToken {
    if (token == null) {
        throw Exception("Token cannot be null")
    }
    if (authProfileId == null) {
        throw Exception("Auth profile id cannot be null")
    }
    return AuthToken(
        accountPk = account_pk,
        token = token,
        authProfileId = authProfileId,
    )
}

fun AuthToken.toEntity(): AuthTokenEntity {
    return AuthTokenEntity(
        account_pk = accountPk,
        token = token,
        authProfileId = authProfileId,
    )
}