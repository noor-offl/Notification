package com.example.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import androidx.appcompat.app.AppCompatActivity
import com.example.notification.databinding.ActivityMainBinding
import com.example.notification.util.AlarmUtil.Companion.setAlarm
import com.example.notification.util.NotificationUtil

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var alarmManager: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent

    companion object {
        const val CHANNEL_ID_DEFAULT = "DC01"
        const val CHANNEL_ID_IMPORTANT = "IMP01"
        const val NOTIFICATION_IMPORTANT_ID = 1001
        const val NOTIFICATION_DEFAULT_ID = 1002
        const val EXTRA_NOTIFICATION_ID = "extraId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as? AlarmManager

        createNotificationChannels()

        binding.btnImpNotify.setOnClickListener {
            NotificationUtil.showNotification(
                this,
                NOTIFICATION_IMPORTANT_ID,
                CHANNEL_ID_IMPORTANT,
                "Important",
                "This is important......"
            )
        }

        binding.btnDefaultNotify.setOnClickListener {
            NotificationUtil.showNotification(
                this,
                NOTIFICATION_DEFAULT_ID,
                CHANNEL_ID_DEFAULT,
                "Default",
                "This is default......"
            )
        }

        binding.btnPermission.setOnClickListener {
            val intent = Intent()
            intent.action = ACTION_REQUEST_SCHEDULE_EXACT_ALARM
            startActivity(intent)
        }

        binding.btnSetAlarm.setOnClickListener {
            setAlarm(this, alarmManager)
        }

    }



    private fun createNotificationChannels() {
        NotificationUtil.createNotificationChannel(
            this,
            CHANNEL_ID_DEFAULT,
            getString(R.string.channel_default),
            getString(R.string.channel_default_description)
        )

        NotificationUtil.createNotificationChannel(
            this,
            CHANNEL_ID_IMPORTANT,
            getString(R.string.channel_important),
            getString(R.string.channel_important_description)
        )
    }
}