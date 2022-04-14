package com.longnp.lnsocial.presentation.main.create

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.longnp.lnsocial.R
import com.longnp.lnsocial.databinding.FragmentCreateBinding

class CreateFragment : BaseCreateFragment() {

    private val viewModel: CreateViewModel by viewModels()

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreen()
        baseCommunicationListener.isStoragePermissionGranted()
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

    private fun fullScreen() {
        (activity as AppCompatActivity).window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }
}