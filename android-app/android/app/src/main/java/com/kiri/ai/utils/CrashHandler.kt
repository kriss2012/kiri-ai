package com.kiri.ai.utils

import android.content.Context
import android.content.Intent
import android.os.Process
import android.util.Log
import kotlin.system.exitProcess

class KiriCrashHandler(private val context: Context) : Thread.UncaughtExceptionHandler {
    private val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        try {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val stackTrace = throwable.stackTraceToString()
            prefs.edit().putString(KEY_LAST_CRASH, stackTrace).apply()
            Log.e("KiriCrash", "CRASH SAVED: $stackTrace")
        } catch (e: Exception) {
            // Silently fail if prefs unavailable
        }

        defaultHandler?.uncaughtException(thread, throwable) ?: run {
            Process.killProcess(Process.myPid())
            exitProcess(10)
        }
    }

    companion object {
        private const val PREFS_NAME = "kiri_crash_prefs"
        private const val KEY_LAST_CRASH = "last_crash_trace"

        fun initialize(context: Context) {
            val handler = KiriCrashHandler(context)
            Thread.setDefaultUncaughtExceptionHandler(handler)
        }

        fun getAndClearLastCrash(context: Context): String? {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val crash = prefs.getString(KEY_LAST_CRASH, null)
            if (crash != null) {
                prefs.edit().remove(KEY_LAST_CRASH).apply()
            }
            return crash
        }
    }
}
