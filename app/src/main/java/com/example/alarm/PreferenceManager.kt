package com.example.alarm

import android.content.SharedPreferences
import com.example.alarm.constant.AlarmEnabled
import com.example.alarm.constant.ShowDialog
import javax.inject.Inject

class PreferenceManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : IPreferenceManager {
    override fun isAlarmEnabled(): Boolean {
        return sharedPreferences.getBoolean(AlarmEnabled, true)
    }

    override fun setAlarmEnabled(isEnabled: Boolean) {
        sharedPreferences.edit().putBoolean(AlarmEnabled, isEnabled).apply()
    }

    override fun isDialogShown(): Boolean {
        return sharedPreferences.getBoolean(ShowDialog, true)
    }

    override fun setDialogShown(isShown: Boolean) {
        sharedPreferences.edit().putBoolean(ShowDialog, isShown).apply()
    }

}