package com.longnp.lnsocial.presentation.main.inbox.addfriend

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lnsocial.R
import com.example.lnsocial.databinding.FragmentAddFriendBinding
import com.longnp.lnsocial.business.domain.models.inbox.Friend
import com.longnp.lnsocial.presentation.main.inbox.BaseInboxFragment

class AddFriendFragment : BaseInboxFragment(), AddFriendListAdapter.InteractionAddFriend {

    private var _binding: FragmentAddFriendBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddFriendViewModel by viewModels()

    private lateinit var adapterAddFriend: AddFriendListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddFriendBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            baseCommunicationListener.displayProgressBar(state.isLoading)

            adapterAddFriend.submitList(state.friends)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapterAddFriend = AddFriendListAdapter(this@AddFriendFragment)
            adapter = adapterAddFriend
        }
    }

    override fun onItemSelected(position: Int, item: List<Friend>) {
        Log.d(TAG, "onItemSelected: " + position)
    }
}