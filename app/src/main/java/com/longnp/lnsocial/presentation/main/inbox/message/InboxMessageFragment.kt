package com.longnp.lnsocial.presentation.main.inbox.message

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.longnp.lnsocial.databinding.FragmentInboxMessageBinding
import com.longnp.lnsocial.presentation.main.inbox.BaseInboxFragment

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

}