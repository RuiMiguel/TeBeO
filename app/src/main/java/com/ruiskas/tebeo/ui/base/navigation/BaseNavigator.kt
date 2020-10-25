package com.ruiskas.tebeo.ui.base.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import org.koin.core.KoinComponent
import org.koin.core.inject

sealed class BaseNavigator : KoinComponent {
    abstract class AppBaseNavigator : BaseNavigator()
    abstract class FeatureBaseNavigator : BaseNavigator()

    private val navigatorLifecycle: NavigatorLifecycle by inject()

    var activity: FragmentActivity?
        get() = navigatorLifecycle.activity
        set(value) {
            navigatorLifecycle.activity = value
        }

    protected val fragment: Fragment?
        get() = activity?.supportFragmentManager?.primaryNavigationFragment

    protected inline fun <reified Type : Fragment> canReload(reload: Boolean): Boolean {
        var canReload = !reload
        if (canReload) {
            val sameFragment = fragment?.childFragmentManager?.fragments?.find { item ->
                item is Type
            }
            canReload = sameFragment?.let { false } ?: true
        }

        return canReload
    }

    protected fun clearBackStackTo(clear: Boolean, @IdRes to: Int, popUpToInclusive: Boolean = false): NavOptions? {
        return if (clear) {
            navOptions {
                popUpTo(
                    id = to,
                    popUpToBuilder = {
                        inclusive = popUpToInclusive
                    }
                )
            }
        } else null
    }
}