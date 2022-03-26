package com.longnp.lnsocial.presentation

interface BaseCommunicationListener {

    fun displayProgressBar(isLoading: Boolean)

    fun hideSoftKeyboard()

    fun isStoragePermissionGranted(): Boolean

    fun changeColorNavigation(isHomePage: Boolean = false)

    fun hideNavigation(isHide: Boolean = false)

}