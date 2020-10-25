package com.ruiskas.tebeo.viewmodel.base

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(dispatcher: CoroutineDispatcher) : ViewModel() {
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("CoroutineException", "CoroutineExceptionHandler handled crash $exception")
    }

    private var scope =
        CoroutineScope(dispatcher + SupervisorJob() + coroutineExceptionHandler)

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        //scope.cancel()
        //TODO: kotlinx.coroutines.JobCancellationException: ProducerCoroutine was cancelled; job=ProducerCoroutine{Cancelled}@c8aef96
    }

    private fun reinitScope(context: CoroutineContext = Dispatchers.IO) {
        if (!scope.isActive) scope =
            CoroutineScope(context + SupervisorJob() + coroutineExceptionHandler)
    }

    fun launch(
        context: CoroutineContext = Dispatchers.IO,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        reinitScope()
        return scope.launch(context) {
            block()
        }
    }

    fun <T> await(
        context: CoroutineContext = Dispatchers.IO,
        block: suspend CoroutineScope.() -> T
    ): Deferred<T> {
        reinitScope()
        return scope.async(context) {
            block()
        }
    }
}