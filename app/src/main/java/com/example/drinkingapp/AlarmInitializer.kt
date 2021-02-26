package com.example.drinkingapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import java.util.*

class AlarmInitializer(val context: FragmentActivity) {

    fun setAlarm() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, AlarmService::class.java).let { intent ->
            PendingIntent.getService(context, R.integer.alarm_intent_request_code, intent, 0)
        }

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 30)
        }

        Toast.makeText(context, "alarm set", Toast.LENGTH_SHORT).show()

        alarmManager.setInexactRepeating(
            AlarmManager.RTC,
            calendar.timeInMillis,
            10 * 1000,
            alarmIntent
        )

        setAutoDisableAlarm()

        saveReminderState(true)
    }

    fun disableAlarm() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, AlarmService::class.java).let { intent ->
            PendingIntent.getService(context, R.integer.alarm_intent_request_code, intent, 0)
        }

        Toast.makeText(context, "alarm disabled", Toast.LENGTH_SHORT).show()

        alarmManager.cancel(alarmIntent)

        saveReminderState(false)
    }

    private fun setAutoDisableAlarm() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar: Calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 1)
            set(Calendar.MINUTE, 50)
        }
        alarmManager.setExact(
            AlarmManager.RTC,
            calendar.timeInMillis,
            "DISABLE_ALARM",
            { ->
                disableAlarm()
            },
            null
        )
    }

    private fun saveReminderState(enabled: Boolean) {
        val sharedPref = context.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putBoolean(context.getString(com.example.drinkingapp.R.string.reminder_state), enabled)
            apply()
        }
    }
}