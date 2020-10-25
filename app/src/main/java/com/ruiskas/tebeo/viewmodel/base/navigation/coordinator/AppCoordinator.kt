package com.ruiskas.tebeo.viewmodel.base.navigation.coordinator

import com.ruiskas.tebeo.ui.navigation.AppNavigator

class AppCoordinator(
    private val appNavigator: AppNavigator
) {
    fun goBack() {
        appNavigator.goBack()
    }

    fun goToStart() {
        appNavigator.goToStart(clearBackStack = true)
    }
}