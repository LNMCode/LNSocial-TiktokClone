package com.longnp.lnsocial.presentation.main.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lnsocial.databinding.FragmentDiscoveryBinding
import com.longnp.lnsocial.business.domain.models.ItemDiscovery

class DiscoveryFragment : BaseDiscoveryFragment(){

    private var _binding: FragmentDiscoveryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DiscoveryViewModel by viewModels()
    private var recyclerViewAdapter: DiscoveryListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoveryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        //binding.swipeRefresh.setOnRefreshListener(this)
        initRecyclerView()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->

            baseCommunicationListener.displayProgressBar(state.isLoading)

            /*processQueue(
                context = context,
                queue = state.queue,
                stateMessageCallback = object : StateMessageCallback {
                    override fun removeMessageFromStack() {
                        viewModel.onTriggerEvent(BlogEvents.OnRemoveHeadFromQueue)
                    }
                })*/

            /*storiesPagerAdapter.apply {
                submitList(blogList = state.videoSeedList)
            }*/

            recyclerViewAdapter?.apply {
                submitList(blogList = state.items)
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(context)

            recyclerViewAdapter = DiscoveryListAdapter()
            adapter = recyclerViewAdapter
        }
    }


}