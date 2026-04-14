package com.kiri.ai.utils

import android.content.Context
import android.content.Intent
import android.os.Process
import android.util.Log
import kotlin.system.exitProcess

class KiriCrashHandler(private val context: Context) : Thread.UncaughtExceptionHandler {
    private val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        val stackTrace = Log.getStackTraceString(throwable)
        Log.e("KiriCrash", "CRASH DETECTED: $stackTrace")

        // You can potentially start a specific ErrorActivity here
        // For now, we just ensure it's logged clearly

        defaultHandler?.uncaughtException(thread, throwable) ?: run {
            Process.killProcess(Process.myPid())
            exitProcess(10)
        }
    }

    companion object {
        fun initialize(context: Context) {
            val handler = KiriCrashHandler(context)
            Thread.setDefaultUncaughtExceptionHandler(handler)
        }
    }
}
