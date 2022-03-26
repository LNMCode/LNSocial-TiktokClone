package com.longnp.lnsocial.presentation.main.create

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.longnp.lnsocial.presentation.BaseCommunicationListener
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ClassCastException

@AndroidEntryPoint
abstract class BaseCreateFragment: Fragment() {

    val TAG = "AppDebug"

    lateinit var baseCommunicationListener: BaseCommunicationListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            baseCommunicationListener = context as BaseCommunicationListener
        }catch (e: ClassCastException) {
            Log.e(TAG, "$context must implement UICommunicationListener" )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseCommunicationListener.changeColorNavigation()
    }
}