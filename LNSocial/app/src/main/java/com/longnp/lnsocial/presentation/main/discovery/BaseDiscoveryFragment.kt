package com.longnp.lnsocial.presentation.main.discovery

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.longnp.lnsocial.presentation.BaseCommunicationListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseDiscoveryFragment: Fragment() {

    val TAG: String = "AppDebug"

    lateinit var baseCommunicationListener: BaseCommunicationListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            baseCommunicationListener = context as BaseCommunicationListener
        }catch(e: ClassCastException){
            Log.e(TAG, "$context must implement UICommunicationListener" )
        }
    }

}