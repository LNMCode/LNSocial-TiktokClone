package com.longnp.lnsocial.presentation.main.inbox.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.longnp.lnsocial.R
import com.longnp.lnsocial.databinding.FragmentInboxMessageBinding
import com.longnp.lnsocial.presentation.main.inbox.BaseInboxFragment
import com.longnp.lnsocial.presentation.util.loadCenterCropImageFromUrl

class InboxMessageFragment : BaseInboxFragment() {

    private var _binding: FragmentInboxMessageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InboxMessageViewModel by viewModels()

    private lateinit var inboxMessageAdapter: InboxMessageAdapter

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
        initEventSendMessage()
        initRecyclerView()
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

            inboxMessageAdapter.apply {
                submitList(state.messages)
            }
        }
    }

    private fun onUpdateView(ava: String, username: String) {
        binding.imageViewProfilePic.loadCenterCropImageFromUrl(ava)
        binding.username.text = username
    }

    private fun cacheValueState() {
        val value = binding.editGchatMessage.text.toString()
        viewModel.onTriggerEvent(InboxMessageEvents.OnUpdateValueMessage(value))
        binding.editGchatMessage.text.clear()
    }

    private fun initEventBackPop() {
        binding.buttonBackPop.setOnClickListener {
            findNavController().popBackStack(R.id.inboxFragment, false)
            baseCommunicationListener.hideNavigation(isHide = false)
        }
    }

    private fun initEventSendMessage() {
        binding.buttonGchatSend.setOnClickListener {
            cacheValueState()
            viewModel.onTriggerEvent(InboxMessageEvents.Send)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerGchat.apply {
            inboxMessageAdapter = InboxMessageAdapter()
            layoutManager = LinearLayoutManager(context)
            adapter = inboxMessageAdapter
        }
    }
}