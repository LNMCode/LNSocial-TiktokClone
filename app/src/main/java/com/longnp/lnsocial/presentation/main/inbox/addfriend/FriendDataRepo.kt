package com.longnp.lnsocial.presentation.main.inbox.addfriend

import android.app.Application
import androidx.annotation.RawRes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.longnp.lnsocial.business.domain.models.discovery.DiscoveryModel
import com.longnp.lnsocial.business.domain.models.inbox.Friend
import javax.inject.Inject

class FriendDataRepo
@Inject
constructor(
    private val application: Application,
){
    fun getStoriesData(@RawRes id: Int): List<Friend>? {
        return loadMockData(id)
    }

    private fun loadMockData(@RawRes id: Int): List<Friend>? {
        val mockData = application.applicationContext.resources.openRawResource(id) //R.raw.stories_data
        val dataString = mockData.bufferedReader().readText()

        val gson = Gson()
        val storiesType = object : TypeToken<List<Friend>>() {}.type
        val storiesDataModelList = gson.fromJson<List<Friend>>(dataString, storiesType)

        return storiesDataModelList
    }
}