package com.longnp.lnsocial.presentation.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.longnp.lnsocial.R
import com.longnp.lnsocial.databinding.FragmentRegisterBinding
import com.longnp.lnsocial.presentation.auth.BaseAuthFragment

class RegisterFragment : BaseAuthFragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEventChangeToLogin()
        initEventBtnLoginOther()
        subscribeObservers()
    }

    private fun initEventChangeToLogin() {
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun initEventBtnLoginOther() {
        binding.btnLoginEmailPassword.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_registerEmailPasswordFragment)
        }
        binding.btnLoginFacebook.setOnClickListener { showToast() }
        binding.btnLoginGoogle.setOnClickListener { showToast() }
    }

    private fun subscribeObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            baseCommunicationListener.displayProgressBar(state.isLoading)
        }
    }

    private fun showToast() {
        Toast.makeText(context, "This future is not available", Toast.LENGTH_LONG).show()
    }

}