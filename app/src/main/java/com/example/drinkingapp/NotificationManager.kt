package com.example.drinkingapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

fun sendNotification(title: String, message: String, context: Context) {
    with(context.let { NotificationManagerCompat.from(it) }) {
        val notificationId = kotlin.random.Random.nextInt();
        this.notify(notificationId, createNotificationBuilder(title, message, context).build())
    }
}

fun createNotificationBuilder(title: String, message: String, context: Context): NotificationCompat.Builder {
    return NotificationCompat.Builder(context, "REMINDER")
        .setSmallIcon(R.drawable.ic_baseline_local_drink_24)
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_MAX)
}

fun createNotificationChannel(name: String, descriptionText: String, notificationManager: NotificationManager) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("REMINDER", name, importance).apply {
            description = descriptionText
        }

        notificationManager.createNotificationChannel(channel)
    }
}

