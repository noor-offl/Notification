package com.example.notification.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import com.example.notification.receiver.AlarmReceiver

class AlarmUtil {
    companion object {
        fun setAlarm(context: Context, alarmManager: AlarmManager?) {
            val alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
                intent.action = "com.example.notification.alarm"
                PendingIntent.getBroadcast(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_MUTABLE
                )
            }
            // Set the alarm to start at 8:30 a.m.
            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()+1000L*30L
                /*set(Calendar.HOUR_OF_DAY, 8)
                set(Calendar.MINUTE, 30)*/
            }
            alarmManager?.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmIntent)
        }

        private fun cancelAlarm() {
            /*val pendingIntent =
                PendingIntent.getService(this, requestId, intent,
                    PendingIntent.FLAG_NO_CREATE)
            if (pendingIntent != null && alarmManager != null) {
                alarmManager!!.cancel(pendingIntent)
            }*/
        }
    }
}