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
            val crashCount = prefs.getInt(KEY_CRASH_COUNT, 0)
            
            if (crashCount > 2) {
                // If we've crashed 3 times in a row, stop trying to handle it
                // and just let it crash to avoid an infinite loop.
                prefs.edit().putInt(KEY_CRASH_COUNT, 0).apply()
                defaultHandler?.uncaughtException(thread, throwable)
                return
            }

            val stackTrace = throwable.stackTraceToString()
            prefs.edit()
                .putString(KEY_LAST_CRASH, stackTrace)
                .putInt(KEY_CRASH_COUNT, crashCount + 1)
                .apply()
            Log.e("KiriCrash", "CRASH SAVED (Count: ${crashCount + 1}): $stackTrace")
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
        private const val KEY_CRASH_COUNT = "crash_count"

        fun initialize(context: Context) {
            val handler = KiriCrashHandler(context)
            Thread.setDefaultUncaughtExceptionHandler(handler)
        }

        fun getAndClearLastCrash(context: Context): String? {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val crash = prefs.getString(KEY_LAST_CRASH, null)
            if (crash != null) {
                // Clear both trace and count on successful retrieval/display
                prefs.edit()
                    .remove(KEY_LAST_CRASH)
                    .putInt(KEY_CRASH_COUNT, 0)
                    .apply()
            }
            return crash
        }
    }
}
