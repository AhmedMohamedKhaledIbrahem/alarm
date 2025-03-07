package com.example.alarm.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.alarm.notification.INotification
import com.example.alarm.constant.NotificationData
import com.example.alarm.notification.NotificationEntity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {
    @Inject lateinit var notification : INotification
    override fun onReceive(context: Context, intent: Intent) {
        val notificationEntity: NotificationEntity? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(NotificationData, NotificationEntity::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(NotificationData)
        }
        notificationEntity?.let {
            notification.showNotification(it)
        }
    }
}