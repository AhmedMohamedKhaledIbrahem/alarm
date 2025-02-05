package com.example.alarm

import android.app.Application
import android.content.Context
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
}