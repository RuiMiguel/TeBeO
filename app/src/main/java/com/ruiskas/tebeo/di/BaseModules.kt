package com.ruiskas.tebeo.di

import com.ruiskas.tebeo.data.logging.DefaultLogger
import com.ruiskas.tebeo.domain.usecases.DoWait
import com.ruiskas.tebeo.ui.base.navigation.NavigatorLifecycle
import com.ruiskas.tebeo.ui.navigation.AppNavigator
import com.ruiskas.tebeo.ui.navigation.DialogNavigator
import com.ruiskas.tebeo.viewmodel.MainViewModel
import com.ruiskas.tebeo.viewmodel.base.navigation.coordinator.AppCoordinator
import com.ruiskas.tebeo.viewmodel.base.navigation.coordinator.DialogCoordinator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

@JvmField
val presentationModule: Module = module {
    single { NavigatorLifecycle() }

    single { AppNavigator() }
    single { AppCoordinator(appNavigator = get()) }

    single { DialogNavigator() }
    single { DialogCoordinator(dialogNavigator = get()) }

    single<CoroutineDispatcher> { Dispatchers.IO }

    viewModel {
        MainViewModel(
            dispatcher = get(),
            appCoordinator = get(),
            doWait = get()
        )
    }
}

@JvmField
val domainModule: Module = module {
    //region UseCases
    factory {
        DoWait()
    }

    //endregion
}

@JvmField
val dataModule: Module = module {

    single { DefaultLogger() }

    //region Repositories


    //endregion

    //region Datasources


    //endregion
}

object Property {
    const val API_URL = "API_URL"
}

@JvmField
val baseModules = listOf(presentationModule, domainModule, dataModule)