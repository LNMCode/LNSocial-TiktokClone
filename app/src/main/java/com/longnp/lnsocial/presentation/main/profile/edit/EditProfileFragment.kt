package com.longnp.lnsocial.presentation.main.profile.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.longnp.lnsocial.R
import com.longnp.lnsocial.databinding.FragmentEditProfileBinding
import com.longnp.lnsocial.presentation.main.profile.BaseProfileFragment

class EditProfileFragment : BaseProfileFragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun initEventBackPop() {
        binding.buttonBackPop.setOnClickListener {
            findNavController().popBackStack(R.id.profileFragment, false)
        }
    }

}