package com.longnp.lnsocial.presentation.auth.register.registerUP

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.longnp.lnsocial.R
import com.longnp.lnsocial.databinding.FragmentRegisterEmailPasswordBinding
import com.longnp.lnsocial.presentation.auth.BaseAuthFragment

class RegisterEmailPasswordFragment : BaseAuthFragment() {

    private var _binding: FragmentRegisterEmailPasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterEmailPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterEmailPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEventBtnRegister()
        intiEventBackPop()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            baseCommunicationListener.displayProgressBar(state.isLoading)
        }
    }

    private fun initEventBtnRegister(){
        binding.btnRegister.setOnClickListener {
            cacheState()
            register()
        }
    }

    private fun intiEventBackPop() {
        binding.buttonBackPop.setOnClickListener {
            findNavController().popBackStack(R.id.registerFragment, false)
        }
    }

    private fun cacheState() {
        val username = binding.username.text.toString()
        val password = binding.password.text.toString()
        val confirmPassword = binding.confrimPassword.text.toString()
        viewModel.onTriggerEvents(RegisterEmailPasswordEvents.OnUpdateUsername(username))
        viewModel.onTriggerEvents(RegisterEmailPasswordEvents.OnUpdatePassword(password))
        viewModel.onTriggerEvents(RegisterEmailPasswordEvents.OnUpdateConfirmPassword(confirmPassword))
    }

    private fun register() {
        viewModel.onTriggerEvents(RegisterEmailPasswordEvents.Register)
    }
}