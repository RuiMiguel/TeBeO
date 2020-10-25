package com.ruiskas.tebeo.ui.base.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.openKeyboard(currentView: View? = null) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(currentView, InputMethodManager.SHOW_FORCED)

    currentView?.requestFocus()
}

fun FragmentActivity.closeKeyboard() {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(window?.decorView?.rootView?.windowToken, 0)
}

fun Fragment.openKeyboard(currentView: View? = null) {
    currentView?.requestFocus()

    val inputMethodManager =
        activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(currentView, InputMethodManager.SHOW_FORCED)
}

fun Fragment.closeKeyboard() {
    val inputMethodManager =
        activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view?.rootView?.windowToken, 0)
}