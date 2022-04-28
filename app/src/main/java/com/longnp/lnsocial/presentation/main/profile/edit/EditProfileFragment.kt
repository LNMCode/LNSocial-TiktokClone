package com.longnp.lnsocial.presentation.main.profile.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.longnp.lnsocial.R
import com.longnp.lnsocial.databinding.FragmentEditProfileBinding
import com.longnp.lnsocial.presentation.main.create.preview.PreviewVideoFragmentArgs
import com.longnp.lnsocial.presentation.main.profile.BaseProfileFragment
import com.longnp.lnsocial.presentation.util.loadCenterCropImageFromUrl
import com.longnp.lnsocial.presentation.util.loadImageFromUrl

class EditProfileFragment : BaseProfileFragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<EditProfileFragmentArgs>()
    private val profileData by lazy { args.profileData }

    private val viewModel: EditProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEventBackPop()
        initView()
        initEvent()
        sub()
    }

    private fun initView() {
        binding.viewUsername.text = profileData.nickName
        binding.imageViewProfilePic.loadCenterCropImageFromUrl(profileData.avatarLink)
    }

    private fun initEvent() {
        binding.viewUsername.setOnClickListener {
            binding.layoutCustomInput.root.isVisible = true
            baseCommunicationListener.hideNavigation(isHide = true)
        }
        binding.layoutCustomInput.closeBtn.setOnClickListener {
            binding.layoutCustomInput.root.isVisible = false
            baseCommunicationListener.hideSoftKeyboard()
            baseCommunicationListener.hideNavigation(isHide = false)
        }
        binding.layoutCustomInput.btnSaveUsername.setOnClickListener {
            val username = binding.layoutCustomInput.username.text.toString()
            cacheData(username)
            binding.layoutCustomInput.username.setText("")
            binding.layoutCustomInput.root.isVisible = false
            baseCommunicationListener.hideSoftKeyboard()
            baseCommunicationListener.hideNavigation(isHide = false)
            viewModel.changeUsername()
        }
    }

    private fun sub() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (state.isUpdateUI) {
                binding.viewUsername.text = state.profile?.nickName
                viewModel.doneUpdateUI()
            }
        }
    }

    private fun initEventBackPop() {
        binding.buttonBackPop.setOnClickListener {
            baseCommunicationListener.hideNavigation(isHide = false)
            findNavController().popBackStack(R.id.profileFragment, false)
        }
    }

    private fun cacheData(username: String) {
        viewModel.cacheData(username)
    }

}