package com.longnp.lnsocial.presentation.main.create.record

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.longnp.lnsocial.R
import com.longnp.lnsocial.databinding.FragmentCreateBinding
import com.longnp.lnsocial.presentation.main.create.BaseCreateFragment
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.controls.Audio
import com.otaliastudios.cameraview.controls.Facing
import com.otaliastudios.cameraview.controls.Flash
import com.otaliastudios.cameraview.controls.Mode
import com.otaliastudios.cameraview.gesture.Gesture
import com.otaliastudios.cameraview.gesture.GestureAction

class CreateFragment : BaseCreateFragment() {

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
        setUpCameraSettings()
        layoutRequestPermission()
        baseCommunicationListener.hideNavigation(isHide = true)
    }

        private fun subscribeObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            baseCommunicationListener.displayProgressBar(state.isLoading)
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
    }

    private fun setUpCameraSettings() {
        binding.flipCameraBtn.setOnClickListener {
            cameraView.facing = if (cameraView.facing == Facing.FRONT) Facing.BACK else Facing.FRONT
        }
        binding.flashBtn.setOnClickListener {
            val isFlashOff = cameraView.flash == Flash.OFF

            cameraView.flash = if (isFlashOff) Flash.ON else Flash.OFF
            binding.flashBtn.setImageResource(
                if (isFlashOff) R.drawable.ic_round_flash_on else R.drawable.ic_round_flash_off
            )
        }
    }

    private fun turnOnCameraPreview(){
        Log.d(TAG, "turnOnCameraPreview: Open camera")
        cameraView.open()
    }

    private fun layoutRequestPermission() {
        if (baseCommunicationListener.isStoragePermissionGranted()) {
            turnOnCameraPreview()
        }
    }

    private fun fullScreen() {
        (activity as AppCompatActivity).window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    override fun onResume() {
        super.onResume()
        layoutRequestPermission()
    }

    override fun onStop() {
        super.onStop()
        cameraView.close()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraView.destroy()
    }
}