package com.ruiskas.tebeo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.ruiskas.tebeo.R
import com.ruiskas.tebeo.databinding.ActivityMainBinding
import com.ruiskas.tebeo.databinding.ViewAlertBinding
import com.ruiskas.tebeo.ui.base.extensions.hideSystemUI
import com.ruiskas.tebeo.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideSystemUI()
        initViewModel()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(findNavController(R.id.nav_host_fragment), null)
    }

    private fun initViewModel() {
        mainViewModel.loadConfiguration()
    }

    fun showSuccess(message: String, onCompleted: () -> Unit) {
        showSuccessAlert(
            containerAlertView = binding.containerAlertView,
            message = message,
            onCompleted = onCompleted
        )
    }

    fun showError(message: String, onCompleted: () -> Unit) {
        showErrorAlert(
            containerAlertView = binding.containerAlertView,
            message = message,
            onCompleted = onCompleted
        )
    }
    private fun showSuccessAlert(
        containerAlertView: ViewAlertBinding,
        message: String,
        onCompleted: () -> Unit = {}
    ) {
        containerAlertView.containerAlert.setBackgroundResource(R.color.green)
        showAlert(containerAlertView, message, onCompleted)
    }

    private fun showErrorAlert(
        containerAlertView: ViewAlertBinding,
        message: String,
        onCompleted: () -> Unit = {}
    ) {
        containerAlertView.containerAlert.setBackgroundResource(R.color.red)
        showAlert(containerAlertView, message, onCompleted)
    }

    private fun showAlert(
        containerAlertView: ViewAlertBinding,
        message: String,
        onCompleted: () -> Unit = {}
    ) {
        containerAlertView.run {
            alertMessageText.text = message
            containerAlert.animate()
                .translationY(containerAlert.height.toFloat())
                .setDuration(1000L)
                .withEndAction {
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(1500)
                        containerAlert.animate()
                            .withEndAction { onCompleted() }
                            .translationY(0f)
                            .duration = 800L
                    }
                }
        }
    }
}