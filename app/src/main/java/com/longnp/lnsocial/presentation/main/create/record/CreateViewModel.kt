package com.longnp.lnsocial.presentation.main.create.record

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.VideoResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateViewModel
@Inject
constructor(

) : ViewModel() {
    private val TAG: String = "AppDebug"

    val state: MutableLiveData<CreateState> = MutableLiveData(CreateState())

    init {
        onTriggerEvent(CreateEvents.NewSearch)
    }

    fun onTriggerEvent(event: CreateEvents) {
        when (event) {
            is CreateEvents.NewSearch -> {
                search()
            }
        }
    }

    private fun search() {

    }

    fun getCameraListener(context: Context) = object : CameraListener() {
        override fun onVideoRecordingStart() {
            super.onVideoRecordingStart()
            Log.d("TAG","Video recording has started")
        }

        override fun onVideoRecordingEnd() {
            super.onVideoRecordingEnd()
            Log.d("TAG","Video recording has ended")
        }

        override fun onVideoTaken(result: VideoResult) {
            super.onVideoTaken(result)
            /*Log.d("TAG","Video has been taken. Result.contentUri ${localRecordLocation?.contentUri} and result.filePath is ${localRecordLocation?.filePath}")


            viewModelScope.launch {
                val duration = getVideoDuration(context)
                Log.d("TAG","duration is $duration")

                _localVideo.value = LocalVideo(
                    localRecordLocation?.filePath,
                    duration?.toLong() ?: 0,
                    timeCreated.toString()
                )
            }*/
        }
    }
}