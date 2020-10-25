package com.ruiskas.tebeo.viewmodel

import com.ruiskas.tebeo.BuildConfig
import com.ruiskas.tebeo.domain.usecases.DoWait
import com.ruiskas.tebeo.viewmodel.base.BaseViewModel
import com.ruiskas.tebeo.viewmodel.base.navigation.coordinator.AppCoordinator
import kotlinx.coroutines.CoroutineDispatcher

class MainViewModel(
    dispatcher: CoroutineDispatcher,
    private val appCoordinator: AppCoordinator,
    private val doWait: DoWait,
) : BaseViewModel(dispatcher) {
    private val SPLASH_TIME = 7L

    fun onBack() = appCoordinator.goBack()

    fun loadConfiguration() {
        launch {
            if (BuildConfig.DEBUG) {
                doWait(DoWait.Params.forTime(2L))
            } else {
                doWait(DoWait.Params.forTime(SPLASH_TIME))
            }

            appCoordinator.goToStart()
        }
    }
}