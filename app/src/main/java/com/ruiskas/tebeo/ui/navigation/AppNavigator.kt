package com.ruiskas.tebeo.ui.navigation

import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.ruiskas.tebeo.MainNavigationDirections
import com.ruiskas.tebeo.R
import com.ruiskas.tebeo.ui.base.navigation.BaseNavigator

class AppNavigator : BaseNavigator.AppBaseNavigator() {
    fun goBack() {
        fragment?.run {
            if (childFragmentManager.backStackEntryCount == 0) {
                activity?.finish()
            } else {
                findNavController().popBackStack()
            }
        } ?: activity?.finish()
    }

    fun goToStart(clearBackStack: Boolean) {
        val clearOptions =
            clearBackStackTo(
                clear = clearBackStack,
                to = R.id.screen_home,
                popUpToInclusive = true
            )

        fragment?.findNavController()
            ?.navigate(MainNavigationDirections.actionToScreenHome(),
                navOptions {
                    popUpTo = clearOptions?.popUpTo ?: -1
                    anim {
                        enter = R.anim.alpha_in
                        exit = R.anim.alpha_out
                        popEnter = R.anim.alpha_in
                        popExit = R.anim.alpha_out
                    }
                })
    }
}