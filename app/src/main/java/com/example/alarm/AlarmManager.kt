package com.example.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AlarmManager @Inject constructor(
    @ApplicationContext private val context: Context
) : IAlarmManager {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun scheduleAlarm(notificationEntity: NotificationEntity) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(NotificationData, notificationEntity)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationEntity.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmTime = notificationEntity.timeStamp * 1000L  //to convert MS

        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)
    }

    override fun cancelAlarm(notificationEntity: NotificationEntity) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationEntity.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }


}