package com.example.alarm.alarm

import com.example.alarm.notification.NotificationEntity

interface IAlarmManager {
    fun scheduleAlarm(notificationEntity: NotificationEntity)
    fun cancelAlarm(notificationEntity: NotificationEntity)
}