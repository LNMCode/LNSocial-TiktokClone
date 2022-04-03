package com.longnp.lnsocial.business.datasource.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.longnp.lnsocial.business.datasource.cache.account.AccountDao
import com.longnp.lnsocial.business.datasource.cache.account.AccountEntity
import com.longnp.lnsocial.business.datasource.cache.auth.AuthTokenDao
import com.longnp.lnsocial.business.datasource.cache.auth.AuthTokenEntity
import com.longnp.lnsocial.business.datasource.cache.profile.ProfileDao
import com.longnp.lnsocial.business.datasource.cache.profile.ProfileEntity

@Database(
    entities = [AuthTokenEntity::class, AccountEntity::class, ProfileEntity::class],
    version = 1
)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAuthTokenDao(): AuthTokenDao

    abstract fun getAccountDao(): AccountDao

    abstract fun getProfileDao(): ProfileDao

    companion object {
        val DATABASE_NAME = "app_db"
    }

}