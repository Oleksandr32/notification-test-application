package com.oleksandrlysun.notificationtestapplication

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.NotificationCompat

class NotificationUtils(private val context: Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private var notificationIdCounter = System.currentTimeMillis().toInt()
    private val notificationsIdMap = mutableMapOf<Int, MutableList<Int>>()

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
    }

    fun showNotification(number: Int) {
        val notificationId = notificationIdCounter++
        val notifyIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(MainActivity.KEY_FRAGMENT_NUMBER, number)
        }
        val notifyPendingIntent = PendingIntent.getActivity(context, notificationId, notifyIntent, PendingIntent.FLAG_ONE_SHOT)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_notification_large))
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_content, number))
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(NotificationCompat.BigTextStyle())
                .setContentIntent(notifyPendingIntent)
                .setAutoCancel(true)
                .build()

        notificationManager.notify(notificationId, notification)
        notificationsIdMap.putOrAdd(number, notificationId)
    }

    fun removeNotifications(number: Int) {
        notificationsIdMap[number]?.forEach {
            notificationManager.cancel(it)
        }
        notificationsIdMap.remove(number)
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)
    }

    private fun MutableMap<Int, MutableList<Int>>.putOrAdd(key: Int, value: Int) {
        val values = get(key)
        if (values != null) {
            values.add(value)
        } else {
            put(key, mutableListOf(value))
        }
    }

    companion object {
        private const val CHANNEL_ID = "com.oleksandrlysun.testapplication.NOTIFICATION_CHANNEL_ID"
        private const val CHANNEL_NAME = "TEST_APPLICATION_NOTIFICATION_CHANNEL"
    }
}