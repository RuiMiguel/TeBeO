package com.ruiskas.tebeo

import android.app.Application
import com.ruiskas.tebeo.di.baseModules
import com.ruiskas.tebeo.ui.base.navigation.NavigatorLifecycle
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TebeoApplication : Application() {
    private val navigator: NavigatorLifecycle by inject()

    override fun onCreate() {
        super.onCreate()

        initDI()
        initActivityLifecycle()
    }

    private fun initDI() {
        startKoin {
            androidContext(applicationContext)
            properties(getExtraProperties())
            printLogger()

            koin.loadModules(baseModules)
            koin.createRootScope()
        }
    }

    private fun getExtraProperties(): HashMap<String, String> {
        val extraProperties = HashMap<String, String>()
        //extraProperties[Property.API_URL] = BuildConfig.API_URL
        return extraProperties
    }

    private fun initActivityLifecycle() {
        registerActivityLifecycleCallbacks(navigator.activityLifecycleCallbacks)
    }
}