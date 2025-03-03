package com.smorzhok.servicestest

import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

class MyIntentService : IntentService(NAME) {

    override fun onHandleIntent(intent: Intent?) {
        log("onStartCommand")
            for (i in 0.. 5) {
                Thread.sleep(1000)
                log("Timer $i")
            }
    }

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        createNotificationChannel()
        val notification = createNotification()
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun createNotification(): Notification{
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Title")
            .setContentText("Text")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()
    }
    private fun createNotificationChannel(){
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notChannel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notChannel)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "MyForegroundService: $message")
    }

    companion object {
        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "channel_name"
        private const val NOTIFICATION_ID = 1
        private const val NAME = "MyIntentService"
        fun newIntent(context: Context): Intent {
            return Intent(context, MyIntentService::class.java)
        }
    }
}