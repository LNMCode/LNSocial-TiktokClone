package com.longnp.lnsocial.business.datasource.cache.account

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.longnp.lnsocial.business.domain.models.Account

@Entity(tableName = "users")
data class AccountEntity (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "pk")
    val pk: String, // is userid

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "scope")
    val scope: String

)

fun AccountEntity.toAccount(): Account {
    return Account(
        pk = pk,
        username = username,
        scope = scope
    )
}

fun Account.toEntity(): AccountEntity {
   return AccountEntity(
       pk = pk,
       username = username,
       scope = scope
   )
}