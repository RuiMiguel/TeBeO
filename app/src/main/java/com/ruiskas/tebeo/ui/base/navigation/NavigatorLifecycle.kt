package com.ruiskas.tebeo.ui.base.navigation

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

class NavigatorLifecycle {
    var activity: FragmentActivity? = null

    var activityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity?) {
        }

        override fun onActivityResumed(activity: Activity?) {
            setCurrentActivity(activity)
        }

        override fun onActivityStarted(activity: Activity?) {
        }

        override fun onActivityDestroyed(activity: Activity?) {
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }

        override fun onActivityStopped(activity: Activity?) {
        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            setCurrentActivity(activity)
        }
    }

    private fun setCurrentActivity(currentActivity: Activity?) {
        //Just save activities for navigation
        if (currentActivity is AppCompatActivity) {
            this.activity = currentActivity
        }
    }
}