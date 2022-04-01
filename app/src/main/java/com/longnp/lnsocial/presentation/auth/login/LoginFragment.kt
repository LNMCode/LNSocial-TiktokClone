package com.longnp.lnsocial.presentation.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.longnp.lnsocial.R
import com.longnp.lnsocial.databinding.FragmentLoginBinding
import com.longnp.lnsocial.presentation.auth.BaseAuthFragment

class LoginFragment : BaseAuthFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        initEventClickLogin()
        initEventClickBackPop()
    }

    private fun initEventClickBackPop() {
        binding.buttonBackPop.setOnClickListener {
            findNavController().popBackStack(R.id.registerFragment, false)
        }
    }

    private fun subscribeObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            baseCommunicationListener.displayProgressBar(isLoading = state.isLoading)
        }
    }

    private fun initEventClickLogin() {
        binding.btnLogin.setOnClickListener {
            cacheState()
            login()
        }
    }

    private fun cacheState() {
        val username = binding.username.text.toString()
        val password = binding.password.text.toString()
        viewModel.onTriggerEvents(LoginEvents.OnUpdateUsername(username))
        viewModel.onTriggerEvents(LoginEvents.OnUpdatePassword(password))
    }

    private fun login() {
        viewModel.onTriggerEvents(LoginEvents.Login)
    }

}