package com.ruiskas.tebeo.data.logging

import android.util.Log

interface CrashLogger {
    fun logError(exception: Throwable, map: Map<String, String?>? = null)
}

class DefaultLogger(private val debuggable: Boolean = false) : CrashLogger {
    override fun logError(exception: Throwable, map: Map<String, String?>?) {
        if(debuggable) Log.e("DefaultLogger", "${exception.message} - $map")
    }
}