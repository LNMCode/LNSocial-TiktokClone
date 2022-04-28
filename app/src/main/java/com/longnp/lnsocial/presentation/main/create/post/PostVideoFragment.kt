package com.longnp.lnsocial.presentation.main.create.post

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.longnp.lnsocial.R
import com.longnp.lnsocial.databinding.FragmentPostVideoBinding
import com.longnp.lnsocial.presentation.main.create.BaseCreateFragment
import com.longnp.lnsocial.presentation.util.loadThumbnailImageFromVideoPath

class PostVideoFragment : BaseCreateFragment() {

    private var _binding: FragmentPostVideoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PostVideoViewModel by viewModels()

    private val args by navArgs<PostVideoFragmentArgs>()
    private val localVideo by lazy { args.localVideo }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostVideoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEventPost()
        initThumbnail()
        initBackPop()
        subscribeObservers()
    }

    private fun initEventPost() {
        binding.postBtn.setOnClickListener {
            baseCommunicationListener.hideSoftKeyboard()
            cacheDate()
            binding.postBtn.isVisible = false
            viewModel.postVideo(
                requireContext(),
                localVideo,
            )
        }
    }

    private fun initThumbnail() {
        binding.videoThumbnail
            .loadThumbnailImageFromVideoPath(
                requireContext(),
                localVideo.filePath
            )
    }

    private fun initBackPop() {
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack(R.id.selectMediaFragment, false)
        }
    }

    private fun subscribeObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            baseCommunicationListener.displayProgressBar(isLoading = state.isLoading)

            if (state.isLoading) {
                binding.loadingLayout.root.isVisible = true
            }

            if (state.isSuccess) {
                Log.d(TAG, "subscribeObservers: Ok luon nha")
                findNavController().popBackStack(R.id.seedFragment, false)
            }
        }
    }

    private fun cacheDate() {
        val description = binding.descriptionText.text.toString()
        viewModel.cacheData(description = description)
    }

}