package com.longnp.lnsocial.presentation.main.create.record

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longnp.lnsocial.business.datasource.local.media.LocalVideo
import com.longnp.lnsocial.business.datasource.local.record.DefaultRecordVideoRepo
import com.longnp.lnsocial.business.datasource.local.record.LocalRecordLocation
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.VideoResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileDescriptor
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class CreateViewModel
@Inject
constructor(
    private val recordVideoRepo: DefaultRecordVideoRepo,
) : ViewModel() {
    private val TAG: String = "AppDebug"

    private var timeCreated by Delegates.notNull<Long>()

    private var localRecordLocation: LocalRecordLocation? = null

    val state: MutableLiveData<CreateState> = MutableLiveData(CreateState())

    private val _localVideo = MutableLiveData<LocalVideo?>()
    val localVideo: LiveData<LocalVideo?> = _localVideo

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

    suspend fun startVideo(context: Context): FileDescriptor? {
        state.value?.let { state ->
            this.state.value = state.copy(
                hasRecordingStarted = true,
                isRecording = true
            )
            timeCreated = System.currentTimeMillis()

            localRecordLocation = recordVideoRepo.initVideo(context, timeCreated)
        }

        return localRecordLocation?.fileDescriptor
    }

    fun resumeVideo() {
        state.value?.let { state ->
            this.state.value = state.copy(
                isRecording = true
            )
        }
    }

    fun pauseVideo() {
        state.value?.let { state ->
            this.state.value = state.copy(
                isRecording = false
            )
        }
    }

    fun stopVideo(context: Context) {
        state.value?.let { state ->
            if (state.hasRecordingStarted) {
                this.state.value = state.copy(
                    hasRecordingStarted = false,
                    isRecording = false
                )
            }

        }
        viewModelScope.launch {
            recordVideoRepo.stopVideo(context)
        }
    }

    fun getCameraListener(context: Context) = object : CameraListener() {
        override fun onVideoRecordingStart() {
            super.onVideoRecordingStart()
            Log.d("TAG", "Video recording has started")
        }

        override fun onVideoRecordingEnd() {
            super.onVideoRecordingEnd()
            Log.d("TAG", "Video recording has ended")
        }

        override fun onVideoTaken(result: VideoResult) {
            super.onVideoTaken(result)
            Log.d("TAG","Video has been taken. Result.contentUri ${localRecordLocation?.contentUri} and result.filePath is ${localRecordLocation?.filePath}")


            viewModelScope.launch {
                val duration = getVideoDuration(context)
                Log.d("TAG","duration is $duration")

                _localVideo.value = LocalVideo(
                    localRecordLocation?.filePath,
                    duration?.toLong() ?: 0,
                    timeCreated.toString()
                )
            }
        }
    }

    suspend fun getVideoDuration(context: Context) = withContext(Dispatchers.IO) {
        val mediaPlayer = MediaPlayer.create(context, localRecordLocation?.filePath?.toUri())
        val duration = mediaPlayer?.duration
        mediaPlayer?.release()

        return@withContext duration
    }

    fun resetLocalVideo() {
        _localVideo.value = null
    }
}