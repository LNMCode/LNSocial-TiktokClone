package com.longnp.lnsocial.presentation.main.inbox

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.longnp.lnsocial.R
import com.longnp.lnsocial.business.domain.models.inbox.InboxModel
import com.longnp.lnsocial.databinding.FragmentInboxBinding
import kotlin.math.log

class InboxFragment : BaseInboxFragment(), InboxListAdapter.InteractionInboxList {

    private var _binding: FragmentInboxBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InboxViewModel by viewModels()

    private lateinit var adapterInbox: InboxListAdapter
    private lateinit var adapterInboxHorizoltal: InboxListHorizolAdapter

    private lateinit var menu: Menu

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentInboxBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolBar)
        initRecyclerView()
        initRecyclerViewHorizontal()
        subscribeObservers()
    }

    private fun initRecyclerView() {
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapterInbox = InboxListAdapter(this@InboxFragment)
            adapter = adapterInbox
        }
    }

    private fun initRecyclerViewHorizontal() {
        binding.recyclerViewHorizontal.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false)
            adapterInboxHorizoltal = InboxListHorizolAdapter()
            adapter = adapterInboxHorizoltal
        }
    }

    private fun subscribeObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            baseCommunicationListener.displayProgressBar(state.isLoading)

            adapterInbox.submitList(blogList = state.inboxModelList)
            adapterInboxHorizoltal.submitList(blogList = state.inboxModelList)
            emptyListMessage(state.inboxModelList.isEmpty())
        }
    }

    private fun emptyListMessage(isEmpty: Boolean) {
        binding.recyclerview.isVisible = !isEmpty
        binding.textEmptyListMessage.isVisible = isEmpty
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        this.menu = menu
        inflater.inflate(R.menu.menu_inbox_fragment, this.menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_friend -> {
                findNavController().navigate(R.id.action_inboxFragment_to_addFriendFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemSelected(position: Int, item: InboxModel) {
        val bundle = bundleOf("model" to item)
        findNavController().navigate(
            R.id.action_inboxFragment_to_inboxMessageFragment2,
            bundle
        )
    }

    override fun onStart() {
        super.onStart()
        viewModel.onTriggerEvent(InboxEvents.NewSearch)
        Log.d(TAG, "onStart: ")
    }

}