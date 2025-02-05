package com.example.alarm

interface IAlarmManager {
    fun scheduleAlarm(notificationEntity: NotificationEntity)
    fun cancelAlarm(notificationEntity: NotificationEntity)
}