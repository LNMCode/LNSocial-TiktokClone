package com.longnp.lnsocial.business.datasource.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.longnp.lnsocial.business.datasource.cache.account.AccountDao
import com.longnp.lnsocial.business.datasource.cache.account.AccountEntity
import com.longnp.lnsocial.business.datasource.cache.auth.AuthTokenDao
import com.longnp.lnsocial.business.datasource.cache.auth.AuthTokenEntity

@Database(entities = [AuthTokenEntity::class, AccountEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getAuthTokenDao(): AuthTokenDao

    abstract fun getAccountDao(): AccountDao


    companion object {
        val DATABASE_NAME = "app_db"
    }

}