package com.promobileapp.chiasenhac.base

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

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

    abstract fun init()
}