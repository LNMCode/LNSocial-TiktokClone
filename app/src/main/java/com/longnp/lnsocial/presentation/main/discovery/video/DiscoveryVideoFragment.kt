package com.longnp.lnsocial.presentation.main.discovery.video

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.longnp.lnsocial.R
import com.longnp.lnsocial.databinding.FragmentDiscoveryVideoBinding
import com.longnp.lnsocial.presentation.main.discovery.BaseDiscoveryFragment
import com.longnp.lnsocial.presentation.main.seeds.list.SeedPagerAdapter

class DiscoveryVideoFragment : BaseDiscoveryFragment() {

    private lateinit var storiesPagerAdapter: SeedPagerAdapter

    private val videoModel: DiscoveryVideoViewModel by viewModels()

    private var _binding: FragmentDiscoveryVideoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoveryVideoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initEventBackPop()
        subscribeObservers()
        baseCommunicationListener.hideNavigation(isHide = true)
    }

    private fun subscribeObservers() {
        videoModel.state.observe(viewLifecycleOwner) { state ->
            baseCommunicationListener.displayProgressBar(state.isLoading)

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

    private fun initEventBackPop() {
        binding.buttonBackPop.setOnClickListener {
            findNavController().popBackStack(R.id.discoveryFragment, false)
            baseCommunicationListener.hideNavigation(isHide = false)
        }
    }


}