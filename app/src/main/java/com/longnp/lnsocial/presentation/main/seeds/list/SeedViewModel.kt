package com.longnp.lnsocial.presentation.main.seeds.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longnp.lnsocial.business.domain.util.Constants
import com.longnp.lnsocial.business.interactors.video.SearchVideoSeeds
import com.longnp.lnsocial.presentation.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class SeedViewModel
@Inject
constructor(
    private val sessionManager: SessionManager,
    private val searchVideoSeeds: SearchVideoSeeds,

) : ViewModel(){

    private val TAG: String = "AppDebug"

    val state: MutableLiveData<SeedState> = MutableLiveData(SeedState())

    init {
        onTriggerEvent(SeedEvents.NewSearch)
    }

    fun onTriggerEvent(event: SeedEvents) {
        when (event) {
            is SeedEvents.NewSearch -> {
                search()
            }
        }
    }

    private fun search() {
        //resetPage()
        //clearList()
        state.value?.let { state ->
            searchVideoSeeds.execute(sessionManager.state.value?.authToken).onEach { dataState ->
                this.state.value = state.copy(isLoading = dataState.isLoading)

                dataState.data?.let { list ->
                    this.state.value = state.copy(videoSeedList = list, isLoading = false)
                }

                dataState.stateMessage?.let { stateMessage ->
                    if(stateMessage.response.message?.contains("ErrorHandling.INVALID_PAGE") == true){
                        //onUpdateQueryExhausted(true)
                    }else{
                        //appendToMessageQueue(stateMessage)
                    }
                    Log.e(TAG, "search: " + stateMessage.response.message)
                }

            }.launchIn(viewModelScope)
        }
    }
}