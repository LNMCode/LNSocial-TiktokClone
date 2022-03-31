package com.longnp.lnsocial.presentation.main.inbox.message

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.longnp.lnsocial.R
import com.longnp.lnsocial.databinding.FragmentInboxMessageBinding
import com.longnp.lnsocial.presentation.main.inbox.BaseInboxFragment
import com.longnp.lnsocial.presentation.util.loadCenterCropImageFromUrl
import kotlin.math.log

class InboxMessageFragment : BaseInboxFragment() {

    private var _binding: FragmentInboxMessageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InboxMessageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInboxMessageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEventBackPop()
        subscribeObservers()
        baseCommunicationListener.hideNavigation(isHide = true)
    }

    private fun subscribeObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            baseCommunicationListener.displayProgressBar(state.isLoading)

            if (state.inboxModel != null) {
                onUpdateView(
                    ava = state.inboxModel.avaReceiver,
                    username = state.inboxModel.nameReceiver
                )
            }
        }
    }

    private fun onUpdateView(ava: String, username: String) {
        binding.imageViewProfilePic.loadCenterCropImageFromUrl(ava)
        binding.username.text = username
    }

    private fun initEventBackPop() {
        binding.buttonBackPop.setOnClickListener {
            findNavController().popBackStack(R.id.inboxFragment, false)
            baseCommunicationListener.hideNavigation(isHide = false)
        }
    }
}