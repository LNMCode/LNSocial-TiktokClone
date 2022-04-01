package com.longnp.lnsocial.presentation.auth

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.longnp.lnsocial.presentation.BaseCommunicationListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseAuthFragment: Fragment() {

    val TAG = "AppDebug"

    lateinit var baseCommunicationListener: BaseCommunicationListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            baseCommunicationListener = context as BaseCommunicationListener
        } catch(e: ClassCastException){
            Log.e(TAG, "$context must implement UICommunicationListener" )
        }
    }
}