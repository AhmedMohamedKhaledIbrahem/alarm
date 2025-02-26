package com.example.alarm

interface IPreferenceManager {
    fun isAlarmEnabled():Boolean
    fun setAlarmEnabled(isEnabled: Boolean)
    fun isDialogShown():Boolean
    fun setDialogShown(isShown: Boolean)

}
