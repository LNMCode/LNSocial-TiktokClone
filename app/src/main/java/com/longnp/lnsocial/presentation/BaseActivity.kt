package com.longnp.lnsocial.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.longnp.lnsocial.business.domain.util.Constants.Companion.READ_STORAGE_REQUEST_CODE
import com.longnp.lnsocial.presentation.session.SessionManager
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), BaseCommunicationListener {
    val TAG: String = "AppDebug";

    private var dialogInView: MaterialDialog? = null

    @Inject
    lateinit var sessionManager: SessionManager

    abstract override fun displayProgressBar(isLoading: Boolean)

    abstract override fun changeColorNavigation(isHomePage: Boolean)

    abstract override fun hideNavigation(isHide: Boolean)

    override fun hideSoftKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager

            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    override fun isStoragePermissionGranted(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO
                ),
                READ_STORAGE_REQUEST_CODE
            )
            return false
        }
        return true;
    }

    // Dismiss dialog when pause application
    override fun onPause() {
        super.onPause()
        if (dialogInView != null) {
            (dialogInView as MaterialDialog).dismiss()
            dialogInView = null
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        hideNavigation(isHide = false)
    }
}