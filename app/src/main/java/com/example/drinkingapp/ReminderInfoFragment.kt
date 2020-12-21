package com.example.drinkingapp

import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.lang.IllegalStateException

class ReminderInfoFragment(val fragmentContext: Context) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val view = requireActivity().layoutInflater.inflate(R.layout.message_input, null)

            builder.setView(view)
                .setPositiveButton(
                    R.string.confirm
                ) { _, _ ->
                    var title = view.findViewById<EditText>(R.id.title_field)?.text.toString()
                    var message = view.findViewById<EditText>(R.id.message_field)?.text.toString()
                    title = if (title.isNullOrBlank()) REMINDER_TITLE else title
                    message = if (message.isNullOrBlank()) REMINDER_MESSAGE else message
                    setRepeatingAlarm(title, message)
                }
                .setNegativeButton(
                    R.string.cancel
                ) { _, _ ->
                    dialog?.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun setRepeatingAlarm(title: String, message: String) {

        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(activity, AlarmService::class.java).let { intent ->
            intent.putExtra("title", title)
            intent.putExtra("message", message)
            PendingIntent.getService(activity, R.integer.alarm_intent_request_code, intent, 0)
        }

        Toast.makeText(context, "alarm set", Toast.LENGTH_SHORT).show()

        alarmManager.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + 5 * 1000,
            alarmIntent
        )

        saveReminderState(true)
    }

    private fun saveReminderState(enabled: Boolean) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putBoolean("REMINDER_STATE", enabled)
            apply()
        }
    }
}