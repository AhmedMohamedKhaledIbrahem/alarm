package com.example.alarm.notification

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationEntity(
    val id :Int,
    val title:String,
    val message:String,
    val timeStamp:Long
):Parcelable
