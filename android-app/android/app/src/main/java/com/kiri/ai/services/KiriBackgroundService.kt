package com.kiri.ai.services

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.kiri.ai.R
import com.kiri.ai.utils.NotificationHelper

/**
 * Kiri High-Performance Background Service
 * 
 * Ensures the app process is prioritized by the Android OS for 'Always-On'
 * connectivity and instant background message delivery.
 */

class KiriBackgroundService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    private fun startForegroundService() {
        val channelId = "kiri_background_service"
        // Use NotificationHelper logic to ensure channel exists
        NotificationHelper.createNotificationChannel(this)

        val notification = NotificationCompat.Builder(this, "kiri_chat_alerts")
            .setSmallIcon(R.drawable.app_logo)
            .setContentTitle("KIRI // ATELIER_ACTIVE")
            .setContentText("INTEL_LINK_ESTABLISHED // ALWAYS_ON")
            .setPriority(NotificationCompat.PRIORITY_LOW) // Silent persistent notification
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setOngoing(true)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(1001, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        } else {
            startForeground(1001, notification)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
