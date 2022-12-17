package com.example.notification.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notification.AboutActivity
import com.example.notification.MainActivity
import com.example.notification.R
import com.example.notification.receiver.NotificationReceiver

class NotificationUtil {
    companion object {
        fun createNotificationChannel(
            context: Context,
            id: String,
            name: String,
            descriptionText: String
        ) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(id, name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }

        fun showNotification(
            context: Context,
            id: Int,
            channelId: String,
            title: String,
            subTitle: String
        ) {

            // Create an explicit intent for an Activity in your app
            val intent = Intent(context, AboutActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            val snoozeIntent = Intent(context, NotificationReceiver::class.java).apply {
                action = "com.example.notification.action.snooze"
                putExtra(MainActivity.EXTRA_NOTIFICATION_ID, 100)
            }

            val snoozePendingIntent: PendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    0,
                    snoozeIntent,
                    PendingIntent.FLAG_MUTABLE
                )

            val pendingIntent: PendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_MUTABLE
            )

            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.notifications_icon)
                .setContentTitle(title)
                .setContentText(subTitle)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .addAction(
                    R.drawable.snooze, context.getString(R.string.snooze),
                    snoozePendingIntent
                )

            with(NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define
                notify(id, builder.build())
            }
        }
    }
}