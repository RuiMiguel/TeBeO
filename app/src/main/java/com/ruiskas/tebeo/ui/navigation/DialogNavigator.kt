package com.ruiskas.tebeo.ui.navigation

import com.ruiskas.tebeo.ui.MainActivity
import com.ruiskas.tebeo.R
import com.ruiskas.tebeo.domain.error.Failure
import com.ruiskas.tebeo.ui.base.navigation.BaseNavigator

class DialogNavigator : BaseNavigator.FeatureBaseNavigator() {
    fun showSuccess(msg: String? = null, onCompleted: () -> Unit = {}) {
        activity?.let {
            val message = msg ?: it.resources.getString(R.string.success)
            (it as MainActivity).showSuccess(message, onCompleted)
        }
    }

    fun showError(failure: Failure? = null, onCompleted: () -> Unit = {}) {
        activity?.let {
            val message = ""

            (it as MainActivity).showError(message, onCompleted)
        }
    }
}