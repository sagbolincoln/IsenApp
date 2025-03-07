package com.example.isenapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

fun scheduleNotification(context: Context, eventTitle: String, delayInSeconds: Int) {
    val intent = Intent(context, NotificationReceiver::class.java).apply {
        putExtra("eventTitle", eventTitle)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context, eventTitle.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val triggerTime = System.currentTimeMillis() + (delayInSeconds * 1000)

    alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
}
