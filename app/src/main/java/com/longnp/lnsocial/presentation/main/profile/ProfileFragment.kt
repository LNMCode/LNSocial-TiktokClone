package com.longnp.lnsocial.presentation.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.longnp.lnsocial.business.domain.models.Profile
import com.longnp.lnsocial.databinding.FragmentProfileBinding
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
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            baseCommunicationListener.displayProgressBar(state.isLoading)

            if (state.profile != null) {
                initViewFromData(state.profile)
            }
        }
    }

    private fun initViewFromData(profile: Profile) {
        binding.titleName.text = profile.nickName
        binding.titleNameA.text = "@${profile.nickName}"
        binding.numberFollowing.text = profile.numberFollowing.toString()
        binding.numberFollower.text = profile.numberFollowers.toString()
        binding.numberLike.text = profile.numberLike.toString()
        binding.imageViewProfilePic.loadCenterCropImageFromUrl(profile.avatarLink)
    }

    private fun initTabViewPager() {
        val adapter = ProfileTabAdapter()
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
            tab.icon = ContextCompat.getDrawable(
                activity?.applicationContext!!,
                TabAdapter.values()[pos].icon
            )
        }.attach()
    }
}