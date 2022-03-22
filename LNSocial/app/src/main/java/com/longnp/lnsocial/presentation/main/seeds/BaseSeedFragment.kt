package com.longnp.lnsocial.presentation.main.seeds

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.longnp.lnsocial.presentation.BaseCommunicationListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseSeedFragment: Fragment() {

    val TAG: String = "AppDebug"

    @Inject
    lateinit var simpleCache: SimpleCache

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