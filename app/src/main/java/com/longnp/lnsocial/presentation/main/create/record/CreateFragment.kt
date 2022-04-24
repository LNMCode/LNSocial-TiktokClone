package com.longnp.lnsocial.presentation.main.create.record

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.longnp.lnsocial.R
import com.longnp.lnsocial.databinding.FragmentCreateBinding
import com.longnp.lnsocial.presentation.main.create.BaseCreateFragment
import com.longnp.lnsocial.presentation.main.create.record.CreateFilterCameraAdapter.*
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.controls.Audio
import com.otaliastudios.cameraview.controls.Facing
import com.otaliastudios.cameraview.controls.Flash
import com.otaliastudios.cameraview.controls.Mode
import com.otaliastudios.cameraview.gesture.Gesture
import com.otaliastudios.cameraview.gesture.GestureAction
import kotlinx.coroutines.launch

class CreateFragment : BaseCreateFragment(), InteractionFilter {

    private val viewModel: CreateViewModel by viewModels()

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    private lateinit var cameraView: CameraView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreen()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        subscribeObservers()
        initEventsClose()
        initCameraView()
        initEventInView()
        initListFilters()
        setUpCameraSettings()
        layoutRequestPermission()
        baseCommunicationListener.hideNavigation(isHide = true)
    }

    private fun subscribeObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            baseCommunicationListener.displayProgressBar(state.isLoading)

            if (state.isRecording) {
                binding.startRecordingBtn.isVisible = false
                binding.pauseRecordingBtn.isVisible = true
                binding.stopRecordingDisplayIcon.isVisible = true
            } else {
                binding.startRecordingBtn.isVisible = true
                binding.pauseRecordingBtn.isVisible = false
                binding.stopRecordingDisplayIcon.isVisible = false
            }
        }
        viewModel.localVideo.observe(viewLifecycleOwner) { localVideo ->
            localVideo?.let {
                findNavController().navigate(
                    CreateFragmentDirections.actionCreateFragmentToPreviewVideoFragment(
                        localVideo
                    )
                )
                viewModel.resetLocalVideo()
            }
        }
    }

    private fun initEventsClose() {
        binding.closeBtn.setOnClickListener {
            findNavController().popBackStack()
            baseCommunicationListener.hideNavigation(isHide = false)
        }
    }

    private fun initCameraView() {
        cameraView = binding.cameraView
        cameraView.apply {
            facing = Facing.FRONT
            audio = Audio.ON
            mode = Mode.VIDEO

            addCameraListener(viewModel.getCameraListener(context))

            mapGesture(Gesture.PINCH, GestureAction.ZOOM) // Pinch to zoom!
            mapGesture(Gesture.TAP, GestureAction.AUTO_FOCUS) // Tap to focus!
        }
    }

    private fun initEventInView() {
        binding.uploadImageBtn.setOnClickListener {
            findNavController().navigate(R.id.action_createFragment_to_selectMediaFragment)
        }
        binding.startRecordingBtn.setOnClickListener {
            // Resume recording
            if (viewModel.state.value?.hasRecordingStarted == true)
                viewModel.resumeVideo()
            // Start recording
            else {
                lifecycleScope.launch {
                    val fileDescriptor =
                        viewModel.startVideo(requireContext()) ?: return@launch
                    cameraView.takeVideo(fileDescriptor)
                }
            }
        }

        binding.pauseRecordingBtn.setOnClickListener {
            cameraView.stopVideo()
            viewModel.pauseVideo()
        }

        binding.finishRecordingBtn.setOnClickListener {
            if (viewModel.state.value?.hasRecordingStarted == true) {
                cameraView.stopVideo()
                viewModel.stopVideo(requireContext())
            }
        }

        // Show camera filters
        binding.filtersBtn.setOnClickListener { cameraFilters() }

        // Hide camera filters
        binding.layoutCameraFilters.closeBtn.setOnClickListener { cameraFilters() }
    }

    private fun initListFilters() {
        binding.layoutCameraFilters.recyclerviewFilter.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            val filtersAdapter = CreateFilterCameraAdapter(this@CreateFragment)
            adapter = filtersAdapter

        }
    }

    private fun cameraFilters() {
        val isShow = binding.layoutCameraFilters.root.isVisible
        binding.layoutCameraFilters.root.isVisible = !isShow
    }

    private fun setUpCameraSettings() {
        binding.flipCameraBtn.setOnClickListener {
            cameraView.facing = if (cameraView.facing == Facing.FRONT) Facing.BACK else Facing.FRONT
        }
        binding.flashBtn.setOnClickListener {
            val isFlashOff = cameraView.flash == Flash.OFF

            cameraView.flash = if (isFlashOff) Flash.TORCH else Flash.OFF
            binding.flashBtn.setImageResource(
                if (isFlashOff) R.drawable.ic_round_flash_on else R.drawable.ic_round_flash_off
            )
        }
    }

    private fun turnOnCameraPreview() {
        Log.d(TAG, "turnOnCameraPreview: Open camera")
        cameraView.open()
    }

    private fun layoutRequestPermission() {
        if (baseCommunicationListener.isStoragePermissionGranted()) {
            turnOnCameraPreview()
        }
    }

    private fun fullScreen() {
        (activity as AppCompatActivity).window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    override fun onResume() {
        super.onResume()
        layoutRequestPermission()
    }

    override fun onStop() {
        super.onStop()
        cameraView.close()
        viewModel.stopVideo(requireContext())

    }

    override fun onDestroy() {
        super.onDestroy()
        cameraView.destroy()
    }

    override fun onItemSelected(item: CreateFilterCamera) {
        cameraView.filter = item.filter.newInstance();
    }
}