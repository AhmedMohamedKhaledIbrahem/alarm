package com.example.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Notification @Inject constructor(
    @ApplicationContext private val context: Context
) : INotification {
    companion object {
        const val CHANNEL_ID = "alarm_channel"
        const val NOTIFICATION_NAME = "Alarm Notifications"
    }

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun showNotification(notificationEntity: NotificationEntity) {
        val channel = NotificationChannel(
            CHANNEL_ID, NOTIFICATION_NAME, NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        println(notificationEntity.id)
        val notification = NotificationCompat.Builder(context , CHANNEL_ID)
            .setContentTitle(notificationEntity.title)
            .setContentText(notificationEntity.message)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        notificationManager.notify(notificationEntity.id , notification)
    }
}