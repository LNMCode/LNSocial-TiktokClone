package com.longnp.lnsocial.presentation.main.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.longnp.lnsocial.R
import com.longnp.lnsocial.business.domain.models.Profile
import com.longnp.lnsocial.databinding.FragmentProfileBinding
import com.longnp.lnsocial.presentation.main.profile.TabAdapter.*
import com.longnp.lnsocial.presentation.util.loadCenterCropImageFromUrl

class ProfileFragment : BaseProfileFragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        initTabViewPager()
        initBtnEventEditProfile()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            baseCommunicationListener.displayProgressBar(state.isLoading)

            if (state.profile != null) {
                initViewFromData(state.profile)
            }
            PUBLIC.setListItemTab(state.videoPublic)
            FAVORITE.setListItemTab(state.videoFavorite)
            PRIVATE.setListItemTab(state.videoPrivate)
        }
    }

    private fun initViewFromData(profile: Profile) {
        binding.titleName.text = profile.nickName
        binding.titleNameA.text = "@${profile.nickName}"
        binding.numberFollowing.text = profile.numberFollowing.toString()
        binding.numberFollower.text = profile.numberFollowers.toString()
        binding.numberLike.text = profile.numberLike.toString()
        binding.description.text = profile.description
        binding.imageViewProfilePic.loadCenterCropImageFromUrl(profile.avatarLink)
    }

    private fun initBtnEventEditProfile() {
        binding.button.setOnClickListener {
            val profileData = viewModel.state.value?.profile
            if (profileData == null) {
                Log.d(TAG, "initBtnEventEditProfile: Profile is null")
                return@setOnClickListener
            }
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment(profileData)
            )
        }
    }

    private fun initTabViewPager() {
        val adapter = ProfileTabAdapter()
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
            tab.tag = values()[pos].name
            tab.icon = ContextCompat.getDrawable(
                activity?.applicationContext!!,
                values()[pos].icon
            )
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.onTriggerEvents(ProfileEvents.GetVideoByType(tab?.tag as String))
                Log.d(TAG, "onTabSelected: ${tab.tag}")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}