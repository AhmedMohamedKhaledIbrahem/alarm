package com.example.alarm.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.alarm.IPreferenceManager
import com.example.alarm.PreferenceManager
import com.example.alarm.alarm.AlarmManager
import com.example.alarm.alarm.IAlarmManager
import com.example.alarm.notification.INotification
import com.example.alarm.notification.Notification
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideNotification(@ApplicationContext context: Context): INotification {
        return Notification(context)
    }

    @Provides
    @Singleton
    fun provideAlarm(@ApplicationContext context: Context): IAlarmManager {
        return AlarmManager(context)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("alarm_prefs", Context.MODE_PRIVATE)

    }

    @Provides
    @Singleton
    fun providePreferenceManager(sharedPreferences: SharedPreferences): IPreferenceManager {
        return PreferenceManager(sharedPreferences)

    }

}