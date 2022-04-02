package com.longnp.lnsocial.presentation.auth.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject
constructor() : ViewModel() {

    private val TAG = "AppDebug"

    val state: MutableLiveData<RegisterState> = MutableLiveData(RegisterState())
}