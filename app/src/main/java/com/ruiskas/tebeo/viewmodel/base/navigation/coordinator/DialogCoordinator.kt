package com.ruiskas.tebeo.viewmodel.base.navigation.coordinator

import com.ruiskas.tebeo.domain.error.Failure
import com.ruiskas.tebeo.ui.navigation.DialogNavigator

class DialogCoordinator(
    private val dialogNavigator: DialogNavigator
) {
    fun showSuccess(message: String? = null, onCompleted: () -> Unit = {}) {
        dialogNavigator.showSuccess(message, onCompleted)
    }

    fun showError(failure: Failure? = null, onCompleted: () -> Unit = {}) {
        dialogNavigator.showError(failure, onCompleted)
    }
}