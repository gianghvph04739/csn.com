package com.promobileapp.chiasenhac.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.promobileapp.chiasenhac.service.MusicPlayerService
import com.promobileapp.chiasenhac.utils.AppConstants

open abstract class BaseActivity : AppCompatActivity() {
    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun showMessage(resID: Int) {
        Toast.makeText(this, getString(resID), Toast.LENGTH_LONG).show()
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun changeStatusBarColor(color: Int, lightStatusBar: Boolean) {
        window?.let { win ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val oldFlags = win.decorView.systemUiVisibility
                win.statusBarColor = color
                var flags = oldFlags
                flags = if (lightStatusBar) {
                    flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                }
                win.decorView.systemUiVisibility = flags
            }
        }
    }

    fun startService(action: String) {
        var intent = Intent(this, MusicPlayerService::class.java)
        intent.setAction(action)
        startService(intent)
    }

    fun playMusic(url: String) {
        var intent = Intent(this, MusicPlayerService::class.java)
        intent.setAction(AppConstants.ACTION_SET_DATASOURCE)
        intent.putExtra(AppConstants.ACTION_SET_DATASOURCE, url)
        startService(intent)
    }

    open fun bindService(connection: ServiceConnection) {
        val intent = Intent(this, MusicPlayerService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    open fun unbindServicePlayMusic(connection: ServiceConnection, mBound: Boolean) {
        if (mBound) {
            try {
                unbindService(connection)
            } catch (ex: Exception) {
            }
        }
    }

    abstract fun init()
}