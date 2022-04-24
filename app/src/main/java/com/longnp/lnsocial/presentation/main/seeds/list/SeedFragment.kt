package com.longnp.lnsocial.presentation.main.seeds.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.longnp.lnsocial.databinding.FragmentSeedBinding
import com.longnp.lnsocial.presentation.main.seeds.BaseSeedFragment

class SeedFragment : BaseSeedFragment(),
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var storiesPagerAdapter: SeedPagerAdapter

    private val viewModel: SeedViewModel by viewModels()

    private var _binding: FragmentSeedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        //binding.swipeRefresh.setOnRefreshListener(this)
        initViewPager()
        subscribeObservers()
        baseCommunicationListener.hideNavigation(isHide = false)
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

            storiesPagerAdapter.apply {
                submitList(blogList = state.videoSeedList)
            }
        }
    }

    private fun initViewPager() {
        storiesPagerAdapter = SeedPagerAdapter(this)
        binding.viewPagerStories.apply {
            adapter = storiesPagerAdapter
            overScrollMode = ViewPager2.OVER_SCROLL_NEVER
        }
    }

    override fun onRefresh() {
        viewModel.onTriggerEvent(SeedEvents.NewSearch)
    }

    override fun onResume() {
        super.onResume()
        onRefresh()
        Log.d(TAG, "onResume: SeedFragment")
    }

}