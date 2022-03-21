package com.longnp.lnsocial.presentation

interface BaseCommunicationListener {

    fun displayProgressBar(isLoading: Boolean)

    fun hideSoftKeyboard()

    fun isStoragePermissionGranted(): Boolean

}