package com.longnp.lnsocial.business.datasource.cache.account

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AccountDao {
    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun searchByUsername(username: String): AccountEntity?

    @Query("SELECT * FROM users WHERE pk = :pk")
    suspend fun searchByPk(pk: Int): AccountEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAndReplace(account: AccountEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnore(account: AccountEntity): Long

    @Query("UPDATE users SET username = :username, scope = :scope WHERE pk = :pk")
    suspend fun updateAccount(pk: Int, scope: Int, username: String)
}