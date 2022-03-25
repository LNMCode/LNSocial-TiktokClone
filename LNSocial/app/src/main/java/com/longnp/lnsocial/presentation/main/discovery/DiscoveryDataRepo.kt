package com.longnp.lnsocial.presentation.main.discovery

import android.app.Application
import com.example.lnsocial.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.longnp.lnsocial.business.domain.models.ItemDiscovery
import javax.inject.Inject

class DiscoveryDataRepo
@Inject
constructor(
    private val application: Application,
){
    fun getStoriesData(): List<ItemDiscovery>? {
        return loadMockData()
    }

    private fun loadMockData(): ArrayList<ItemDiscovery>? {
        val mockData = application.applicationContext.resources.openRawResource(R.raw.stories_data)
        val dataString = mockData.bufferedReader().readText()

        val gson = Gson()
        val storiesType = object : TypeToken<ArrayList<ItemDiscovery>>() {}.type
        val storiesDataModelList = gson.fromJson<ArrayList<ItemDiscovery>>(dataString, storiesType)

        return storiesDataModelList
    }
}